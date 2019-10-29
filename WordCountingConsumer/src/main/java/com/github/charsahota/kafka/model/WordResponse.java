package com.github.charsahota.kafka.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

/**
 * Model object for returning word response.
 * 
 * @author Char Sahota
 * @date Oct. 26, 2019
 *
 */
@XmlRootElement
public class WordResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7592966308119269612L;
	
	private String word;
	private String wordCount;
	private String firstDate;
	private String lastDate;
	private String status;
	private String message;

	public WordResponse(String word) {
		this.word = word;
	}


	public String getWord() {
		return word;
	}


	public void setWord(String word) {
		this.word = word;
	}


	public String getWordCount() {
		return wordCount;
	}


	public void setWordCount(String wordCount) {
		this.wordCount = wordCount;
	}


	public String getFirstDate() {
		return firstDate;
	}


	public void setFirstDate(String firstDate) {
		this.firstDate = firstDate;
	}


	public String getLastDate() {
		return lastDate;
	}


	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstDate == null) ? 0 : firstDate.hashCode());
		result = prime * result + ((lastDate == null) ? 0 : lastDate.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		result = prime * result + ((wordCount == null) ? 0 : wordCount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordResponse other = (WordResponse) obj;
		if (firstDate == null) {
			if (other.firstDate != null)
				return false;
		} else if (!firstDate.equals(other.firstDate))
			return false;
		if (lastDate == null) {
			if (other.lastDate != null)
				return false;
		} else if (!lastDate.equals(other.lastDate))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		if (wordCount == null) {
			if (other.wordCount != null)
				return false;
		} else if (!wordCount.equals(other.wordCount))
			return false;
		return true;
	}

}
