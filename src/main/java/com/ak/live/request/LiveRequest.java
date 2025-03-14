package com.ak.live.request;

import java.sql.Date;

import com.ak.live.enums.Genre;
import com.ak.live.enums.Language;

import lombok.Data;
@Data
public class LiveRequest {
	private String LiveName;
	private Integer duration;
	private Double rating;
	private Date releaseDate;
	private Genre genre;
	private Language language;
}
