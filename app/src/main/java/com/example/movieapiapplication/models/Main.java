package com.example.movieapiapplication.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Main{

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("total_results")
	private int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}

}