/**
 * Created by Nokid on 12/07/16.
 */
'use strict';

var model={
    questions:[],
    user:{},
    countOfChapters:5,
    lectures:[]
};

var brmApp = angular.module("brmApp",['ui.materialize','pdf','ui.router']);

//Router Provider
brmApp.config(function ($stateProvider,$urlRouterProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('home',{
         url:'/',
            templateUrl:'html/home.html',
            controller:'MainAppCtrl'
     })
        .state('questions',{
            url:'/questions',
            templateUrl:'html/questionsTab.html',
            controller:'MainAppCtrl'
    })
        .state('pdfLectures',{
            url:'/pdfLectures',
            controller:'PdfLecturesCtrl',
            templateUrl: 'html/pdfTab.html'

    })
        .state('exercises',{
            url:'/exercises',
            templateUrl:'html/exercisesTab.html',
            controller:'MainAppCtrl'
        }).state('addNewQuestion',{
        url:'/addNewQuestion',
        templateUrl:'html/addNewQuestionTab.html',
        controller:'addNewQuestionTabCtrl'
    });
});

//MainController
brmApp.controller("MainAppCtrl", function ($scope,$http) {
    $scope.data=model;

    $scope.servUrl="http://85.214.195.89:8080/api";
    $scope.testUrl="questions.json";

    $http.get($scope.servUrl+'/questions/getAll').success(function (data, status, headers, config) {
        if(data){
            $scope.data.questions=data;
            $scope.actualQuestion=$scope.data.questions[0];
            console.log($scope.actualQuestion.possibleAnswers[0].answer);
        }
    });

    $scope.data.user={name : "Peter Griffin",isAdmin : true,id : "9363bdobe"};//TODO Dummy

    /*$http.post($scope.servUrl+'/questions/pushQuestions',$scope.testPostQuestion).then(function successCallback(response) {
        // this callback will be called asynchronously
        // when the response is available
        console.log(response);
    }, function errorCallback(response) {
        // called asynchronously if an error occurs
        // or server returns response with an error status.
        console.log(response);
    });*/


    $scope.testAlert=function(){
      alert('hello');
    };
    $scope.goToQuestion=function (question) {
        $scope.actualQuestion=question;
    };

    $scope.getAllTopics=function () {//TODO Dummy
        $scope.data.topics=["Start","Accounting","Year End","More Accounting","Weather"];
        return $scope.data.topics;
    };

    $scope.isUserAdmin=function () {
       return $scope.data.user.isAdmin;
    };

});


brmApp.controller('addNewQuestionTabCtrl',function ($scope, $http) {
    $scope.newQuestion={};
    $scope.servUrl="http://85.214.195.89:8080/api";

    $scope.onSubmit=function () {
        console.log($scope.newQuestion.content);
        console.log($scope.newQuestion.possibleAnswers);
        console.log($scope.newQuestion.hint);
        console.log($scope.newQuestion.correctAnswerId);
        console.log($scope.newQuestion.isBookingEntry);
        console.log($scope.newQuestion.fromPage);
        console.log($scope.newQuestion.toPage);
        console.log($scope.newQuestion.topic);
        console.log($scope.newQuestion.chapter);


    var questionsToSend=[$scope.newQuestion];
        questionsToSend[0].possibleAnswers=[{answerId:0 , answer:$scope.newQuestion.possibleAnswers[0]},
            {answerId:1 , answer:$scope.newQuestion.possibleAnswers[1]},
            {answerId:2 , answer:$scope.newQuestion.possibleAnswers[2]},
            {answerId:3 , answer:$scope.newQuestion.possibleAnswers[3]}];
        console.log(questionsToSend);
        $http.post($scope.servUrl+'/questions/pushQuestions',questionsToSend).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            console.log(response);

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(response);

        });


    };

});

//PDF Controller
brmApp.controller('PdfLecturesCtrl',function ($scope, $http) {

    $scope.pdfUrl = 'f.txt.pdf';

    $scope.data=model;

    $scope.data.lectures=[{
        title:'Einführung',
        pdfUrl:'08-Persistenz.pdf'
    },{
        title:'Second Session',
        pdfUrl:'f.txt.pdf'
    },{
        title:'Third Session',
        pdfUrl:'08-Persistenz.pdf'
    },{
        title:'Final Session',
        pdfUrl:'f.txt.pdf'
    }];

    $scope.getNavStyle = function(scroll) {
        if(scroll > 100) return 'pdf-controls fixed';
        else return 'pdf-controls';
    };

    $scope.getRangeForLectures=function () {
        return $scope.data.lectures;
    };

    $scope.goToLecture=function (lecture) {
        $scope.pdfUrl = lecture.pdfUrl;
    };
});

brmApp.directive('myQuestion',['$http',function ($http) {
    var linkFn=function (scope, element, attrs, controller, transcludeFn) {
      console.log(scope);
      console.log(element.contents());
      console.log(attrs);
      console.log(controller);
      console.log(transcludeFn);
        console.log(scope.data.user);

        var isQuestionChanged = false;

        var setNormalQuestionView=function () {
            element.find("#content").replaceWith('<h5 class="center-align" id="content">'+scope.actualQuestion.content+'</h5>');
            element.find("#answerA").replaceWith('<a href="#!" class="collection-item" id="answerA">'+scope.actualQuestion.possibleAnswers[0].answer+'</a>');
            element.find("#answerB").replaceWith('<a href="#!" class="collection-item" id="answerB">'+scope.actualQuestion.possibleAnswers[1].answer+'</a>');
            element.find("#answerC").replaceWith('<a href="#!" class="collection-item" id="answerC">'+scope.actualQuestion.possibleAnswers[2].answer+'</a>');
            element.find("#answerD").replaceWith('<a href="#!" class="collection-item" id="answerD">'+scope.actualQuestion.possibleAnswers[3].answer+'</a>');

            if(scope.data.user.isAdmin){
                element.find("#editButton").text('Edit');
            }else{
                //alert();
                //element.find("#editButton").remove();
            }

            isQuestionChanged=false;
        };


        angular.element(document).ready(function () {
            scope.$watch('actualQuestion',function () {
                setNormalQuestionView();
            });
        });

      var editButton = element.find("a#editButton");
      editButton.bind('click',function () {
          if(editButton.text()==='Save'){
              onSaveClick(editButton);
          }else if(editButton.text()==='Edit'){
             onEditClick(editButton);
          }

      });

        var onEditClick= function (editButton) {

            isQuestionChanged=true;

            editButton.text('Save');

            element.find("#content").replaceWith('<input placeholder="Placeholder" id="content" type="text" value="'+scope.actualQuestion.content+'" class="validate">');
            element.find("#answerA").replaceWith('<input placeholder="Placeholder" id="answerA" type="text" value="'+scope.actualQuestion.possibleAnswers[0].answer+'" class="validate">');
            element.find("#answerB").replaceWith('<input placeholder="Placeholder" id="answerB" type="text" value="'+scope.actualQuestion.possibleAnswers[1].answer+'" class="validate">');
            element.find("#answerC").replaceWith('<input placeholder="Placeholder" id="answerC" type="text" value="'+scope.actualQuestion.possibleAnswers[2].answer+'" class="validate">');
            element.find("#answerD").replaceWith('<input placeholder="Placeholder" id="answerD" type="text" value="'+scope.actualQuestion.possibleAnswers[3].answer+'" class="validate">');

        };

        var onSaveClick = function (saveButton) {
            if(checkNoEmptyFields()){
                sendUpdatedQuestionToServer();

                setNormalQuestionView();
            }else{
                alert('Please fill all fields!');
            }

        };



        function sendUpdatedQuestionToServer() {
            var questionToSend = [scope.actualQuestion];

            questionToSend[0].content = element.find("#content").val();

            questionToSend[0].possibleAnswers=[{answerId:0,answer:element.find("#answerA").val()},
                {answerId:0,answer:element.find("#answerB").val()},
                {answerId:0,answer:element.find("#answerC").val()},
                {answerId:0,answer:element.find("#answerD").val()}];
            console.log(questionToSend);
            $http.post(scope.servUrl+'/questions/pushQuestions',questionToSend).then(function successCallback(response) {
             // this callback will be called asynchronously
             // when the response is available
             console.log(response);

             }, function errorCallback(response) {
             // called asynchronously if an error occurs
             // or server returns response with an error status.
             console.log(response);

             });
        }

        function checkNoEmptyFields() {
            return element.find("#content").attr('value')!==''&&
            element.find("#answerA").attr('value')!==''&&
            element.find("#answerB").attr('value')!==''&&
            element.find("#answerC").attr('value')!==''&&
            element.find("#answerD").attr('value')!=='';
        }

    };
    return{
        restrict : 'E',
        scope : true,
        templateUrl: 'html/mainContentMC.html',
        require:"^?ngShow",
        link:linkFn
    }
}]);