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
            templateUrl:'html/questionsTab.html',
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
    })
        .state('addNewQuestion',{
            url:'/addNewQuestion',
            templateUrl:'html/addNewQuestionTab.html',
            controller:'addNewQuestionTabCtrl'
    });
});

//Model
brmApp.factory('brmFactory',['$http',function ($http) {
    var servUrl="http://bilanzportal.de/api";

    //Get Model Items

    //Get all questions and add to the factory
    $http.get($scope.servUrl+'/questions/getAll').success(function (data, status, headers, config) {
        if(data){
            this.questions=data;
            this.actualQuestion=this.questions[0];
        }
    });
    //Get all topics and add to the factory
    $http.get($scope.servUrl+'/topics/getAll').success(function (data, status, headers, config) {
        if(data){
            this.topics=data;
        }
    });
    //Get all lectures and add to the factory
    $http.get($scope.servUrl+'/lectures/getAll').success(function (data, status, headers, config) {
        this.lectures=data;
    });
    //Get all questions from lecture by the page and add to the factory
    //TODO by page AND lecture name
    this.getAllQuestionsFromLecture=function (pageNumber) {
        $http.get($scope.servUrl+'/lectures/getQuestions',{params:{page:pageNumber}}).success(function (data, status, headers, config) {
            $scope.processQuestions(data);
        });
    };

    //Add or Update model items

    //Send new question(s) to the server
    this.updateOrAddQuestions=function(arrayOfQuestionsToSend){
        $http.post($scope.servUrl+'/questions/pushQuestions',arrayOfQuestionsToSend).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            Materialize.toast('New topic added', 4000);
            console.log(response);

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(response);

        });
    };

    //Add new topic to the model
    this.addnewTopic=function (newTopic) {
        $http.post($scope.servUrl+'/topics/addTopic',newTopic).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            Materialize.toast('New topic added', 4000);
            console.log(response);

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(response);

        });
    };

}]);

//MainController
brmApp.controller("MainAppCtrl", function ($scope,$http) {
    $scope.data=model;



    //TODO delete for testing only

    $scope.testSplitPattern=function () {
        var str = "1300: Sonstige Forderungen, 60.000 an 0240: Geschäftsbauten, 55.000 3800: UST, 5.000";
        var arr = str.split(' an ');
        var leftSide= arr[0].match(/\d{4}:\s[^,]+,\s\d+\.\d+/g);
        var rightSide=arr[1].match(/\d{4}:\s[^,]+,\s\d+\.\d+/g);

        console.log(leftSide);
        console.log(rightSide);

    };





    $scope.servUrl="http://bilanzportal.de/api";

    $http.get($scope.servUrl+'/questions/getAll').success(function (data, status, headers, config) {
        if(data){
            $scope.data.questions=data;
            $scope.actualQuestion=$scope.data.questions[0];
        }
    });

    $http.get($scope.servUrl+'/topics/getAll').success(function (data, status, headers, config) {
        if(data){
            $scope.data.topics=data;
        }
    });

    $scope.data.user={isAdmin : false};//TODO Dummy


    $scope.isChoosingAnswerEnabled=true;


    $scope.goToQuestion=function (question) {
        $scope.actualQuestion=question;
        $scope.isChoosingAnswerEnabled=true;
    };

    $scope.getAllTopics=function () {
        return $scope.data.topics;
    };

    $scope.isUserAdmin=function () {
       return $scope.data.user.isAdmin;
    };


    $scope.testSplitPattern();

});

brmApp.controller("LoginCtrl",function ($rootScope, $scope, $http, $location) {
    $scope.servUrl="http://bilanzportal.de/";

    var authenticate = function(credentials, callback) {
        var data = credentials ? "username=" + credentials.email + "&password=" + credentials.password + "&submit=Login"
            : "username=1&password=1&submit=Login";

        $http({
            method: 'POST',
            url: $scope.servUrl+"/login",
            data: data,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function (data, status) {
            $http({
                method: 'GET',
                url: $scope.servUrl+"/user"
            }).then(function(response) {
                if(response) $rootScope.authenticated = true;
                else $rootScope.authenticated = false;
                callback && callback();
            }, function() {
                $rootScope.authenticated = false;
                console.error();
            });
        }).error(function (error) {
            console.log(error);
            $rootScope.authenticated = false;
            callback && callback();
        })
    };

    authenticate();
    self.credentials = {email:$scope.emailLogin,
                        password:$scope.passwordLogin};
    $scope.login = function() {
        authenticate(self.credentials, function() {
            if ($rootScope.authenticated) {
                $location.path("/questions");
                self.error = false;
            } else {
                $location.path("/");
                self.error = true;
            }
        });
    };
    $scope.testHelloOutput=function(){
      console.log("Hello");
    };

    self.logout = function() {
        $http.post('logout', {}).finally(function() {
            $rootScope.authenticated = false;
            $location.path("/");
        });
    }
});

brmApp.controller('addNewQuestionTabCtrl',function ($scope, $http) {
    $scope.newQuestion={};
    $scope.data={};
    $scope.data.topics={};
    $scope.servUrl="http://bilanzportal.de/api";

    $http.get($scope.servUrl+'/topics/getAll').success(function (data, status, headers, config) {

     $scope.data.topics=data;
     console.log($scope.data.topics);
     });

    $scope.getAllTopics=function () {//TODO delete dummy
        return $scope.data.topics;
    };

    $scope.onSubmit=function () {

    var questionsToSend=[$scope.newQuestion];
        questionsToSend[0].possibleAnswers=[{answerId:0 , answer:$scope.newQuestion.possibleAnswers[0]},
            {answerId:1 , answer:$scope.newQuestion.possibleAnswers[1]},
            {answerId:2 , answer:$scope.newQuestion.possibleAnswers[2]},
            {answerId:3 , answer:$scope.newQuestion.possibleAnswers[3]}];
        var topicIndex=$scope.newQuestion.topic;

        questionsToSend[0].topic = $scope.data.topics[topicIndex];
        questionsToSend[0].chapter =topicIndex;

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

    $scope.newTopic={};

    $scope.onSubmitTopic=function () {
        var topicToSend = $scope.newTopic;
        console.log(topicToSend);
        $http.post($scope.servUrl+'/topics/addTopic',topicToSend).then(function successCallback(response) {
            // this callback will be called asynchronously
            // when the response is available
            Materialize.toast('New topic added', 4000);
            console.log(response);

        }, function errorCallback(response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            console.log(response.status);

        });
    };

    $scope.newLecture={};
});

//PDF Controller
brmApp.controller('PdfLecturesCtrl',function ($scope, $http) {
    $http.get($scope.servUrl+'/lectures/getAll').success(function (data, status, headers, config) {
        $scope.data.lectures=data;
    });

    $scope.servUrl="http://bilanzportal.de/api";
    $scope.pdfUrl = $scope.servUrl + '/lectures/' + $scope.data.lectures[0];

    $scope.showQuestionForPage = false;
    $scope.actualQuestion = {};

    $scope.loadingProgress=0;
    $scope.isLoaded=false;
    $scope.isChoosingAnswerEnabled=true;

    $scope.data=model;
    $scope.data.user={name : "Peter Griffin",isAdmin : false,id : "9363bdobe"};//TODO Dummy



    $scope.actualLecture = $scope.data.lectures[0];
        $scope.getNavStyle = function(scroll) {
        if(scroll > 100) return 'pdf-controls fixed';
        else return 'pdf-controls';
    };

    $scope.getRangeForLectures=function () {
        return $scope.data.lectures;
    };

    $scope.goToLecture=function (lecture) {
        if(lecture!==$scope.actualLecture){
            $scope.getNewLectureUrl=$scope.servUrl+'/lectures/';
            $scope.pdfUrl = $scope.getNewLectureUrl+lecture.name;
            $scope.actualLecture=lecture;
            $scope.isLoaded=false;
            $scope.showQuestionForPage = false;
        }

    };

    $scope.onProgress = function(progress) {
        // handle a progress bar
        $scope.loadingProgress = progress.loaded / progress.total*100;
        console.log($scope.loadingProgress);
    };

    $scope.onLoad = function() {
        // do something when pdf is fully loaded
        // $scope.loading = '';
        $scope.isLoaded=true;
    };

    $scope.onPageChange=function (pageNumber) {
        console.log(pageNumber);

        $http.get($scope.servUrl+'/lectures/getQuestions',{params:{page:pageNumber}}).success(function (data, status, headers, config) {
            $scope.processQuestions(data);
        });
        $scope.isChoosingAnswerEnabled=true;
    };

    $scope.processQuestions=function (questions) {
        if(questions.length>0){
            $scope.data.questions = questions;
            $scope.showQuestionForPage=true;
            $scope.actualQuestion = questions[0];
        }else{
            $scope.showQuestionForPage=false;
        }
    };
});

brmApp.directive('myQuestion',['$http','$compile','$timeout',function ($http,$compile,$timeout) {
    var linkFn=function (scope, element, attrs, controller, transcludeFn) {
        scope.servUrl="http://bilanzportal.de/api";
        scope.isChoosingAnswerEnabled=true;

         scope.onAnswerClick = function (event,answerId) {
             console.log(event);
             var rightAnswerLetter;
             console.log(scope.actualQuestion);
             var rightAnswerId=scope.actualQuestion.correctAnswerId;

             //TODO Make it better
             switch (rightAnswerId){
                 case 0:
                     rightAnswerLetter='A';
                     break;
                 case 1:
                     rightAnswerLetter='B';
                     break;
                 case 2:
                     rightAnswerLetter='C';
                     break;
                 case 3:
                     rightAnswerLetter='D';
                     break;
             }

             var elem =angular.element(event.target);
             scope.isChoosingAnswerEnabled=false;
             if(answerId===rightAnswerId){
                 elem.addClass('green lighten-3');
                 //Todo Implement send to server
             }else{
                 elem.addClass('red lighten-3');
                 angular.element(document).find('#answer'+rightAnswerLetter).addClass('green lighten-3');
             }
         };

        //Normal mode = choosing NOT editing
        scope.setNormalQuestionView=function () {
            console.log(1);
            var contentTemplate = $compile('<h5 class="center-align" id="content">'+scope.actualQuestion.content+'</h5>')(scope);
            var answerATemplate = $compile('<a ng-click="!isChoosingAnswerEnabled||onAnswerClick($event,0)" class="collection-item" id="answerA">'+scope.actualQuestion.possibleAnswers[0].answer+'</a>')(scope);
            var answerBTemplate = $compile('<a ng-click="!isChoosingAnswerEnabled||onAnswerClick($event,1)" class="collection-item" id="answerB">'+scope.actualQuestion.possibleAnswers[1].answer+'</a>')(scope);
            var answerCTemplate = $compile('<a ng-click="!isChoosingAnswerEnabled||onAnswerClick($event,2)" class="collection-item" id="answerC">'+scope.actualQuestion.possibleAnswers[2].answer+'</a>')(scope);
            var answerDTemplate = $compile('<a ng-click="!isChoosingAnswerEnabled||onAnswerClick($event,3)" class="collection-item" id="answerD">'+scope.actualQuestion.possibleAnswers[3].answer+'</a>')(scope);


            element.find("#content").replaceWith(contentTemplate);
            element.find("#answerA").replaceWith(answerATemplate);
            element.find("#answerB").replaceWith(answerBTemplate);
            element.find("#answerC").replaceWith(answerCTemplate);
            element.find("#answerD").replaceWith(answerDTemplate);

            if(scope.data.user.isAdmin){
                element.find("#editButton").text('Edit');
            }else{
                //alert();
                //element.find("#editButton").remove();
            }

        };

        //Set listener on question change to quit editing
        angular.element(document).ready(function () {
            scope.$watch('actualQuestion',function () {
                scope.setNormalQuestionView();
            });
        });

        //Set OnClick on Edit/Save Button
      var editButton = element.find("a#editButton");
      editButton.bind('click',function () {
          if(editButton.text()==='Save'){
              onSaveClick(editButton);
          }else if(editButton.text()==='Edit'){
             onEditClick(editButton);
          }

      });

        //Functionality of previous button
        var previousButton = element.find("a#previousButton");
        previousButton.bind('click',function () {
                var index = scope.data.questions.indexOf(scope.actualQuestion)-1;
                if(index>=0||index<scope.data.questions.length){
                    scope.actualQuestion = scope.data.questions[index];
                    scope.isChoosingAnswerEnabled=true;
                    scope.$apply();//Todo make it without apply
                }


        });
        //Functionality of next button
        var nextButton = element.find("a#nextButton");
        nextButton.bind('click',function () {
                var index = scope.data.questions.indexOf(scope.actualQuestion)+1;
                if(index>=0||index<scope.data.questions.length){
                    scope.actualQuestion = scope.data.questions[index];
                    scope.isChoosingAnswerEnabled=true;
                    scope.$apply();//Todo make it without apply
                }
        });


        //What happens if Edit Button was clicked
        var onEditClick= function (editButton) {

            editButton.text('Save');
            var contentTemplate = $compile('<input placeholder="Question" id="content" type="text" value="'+scope.actualQuestion.content+'" class="validate">')(scope);
            var answerATemplate = $compile('<input placeholder="Answer A" id="answerA" type="text" value="'+scope.actualQuestion.possibleAnswers[0].answer+'" class="validate">')(scope);
            var answerBTemplate = $compile('<input placeholder="Answer B" id="answerB" type="text" value="'+scope.actualQuestion.possibleAnswers[1].answer+'" class="validate">')(scope);
            var answerCTemplate = $compile('<input placeholder="Answer C" id="answerC" type="text" value="'+scope.actualQuestion.possibleAnswers[2].answer+'" class="validate">')(scope);
            var answerDTemplate = $compile('<input placeholder="Answer D" id="answerD" type="text" value="'+scope.actualQuestion.possibleAnswers[3].answer+'" class="validate">')(scope);

            element.find("#content").replaceWith(contentTemplate);
            element.find("#answerA").replaceWith(answerATemplate);
            element.find("#answerB").replaceWith(answerBTemplate);
            element.find("#answerC").replaceWith(answerCTemplate);
            element.find("#answerD").replaceWith(answerDTemplate);

        };

        //What happens if Edit Button was clicked
        var onSaveClick = function (saveButton) {
            if(checkNoEmptyFields()){
                scope.sendUpdatedQuestionToServer();
                scope.setNormalQuestionView();
            }else{
                alert('Please fill all fields!');
            }

        };

        //Help functions
        //Get data from DOM. Set it to actualQuestion. Send ActualQuestion to server
        scope.sendUpdatedQuestionToServer=function () {
            var questionToSend = [scope.actualQuestion];

            questionToSend[0].content = element.find("#content").val();

            questionToSend[0].possibleAnswers=[
                {answerId:0,answer:element.find("#answerA").val()},
                {answerId:1,answer:element.find("#answerB").val()},
                {answerId:2,answer:element.find("#answerC").val()},
                {answerId:3,answer:element.find("#answerD").val()}
                ];
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
        };

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
        scope : {actualQuestion:"=question",
                isChoosingAnswerEnabled:"=isChoosingEnabled",
                data:"="},
        templateUrl: 'html/mainContentMC.html',
        require:["^?ngShow","^?ngClick"],
        link:linkFn
    }
}]);