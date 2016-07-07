package com.fortum.nokid.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by yuriy on 07.04.16.
 */

public class PagePK implements Serializable {
    private long vorlesungId;
    private int from;
    private int to;
}
