package com.github.charsahota.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Modal object for submitting text to the Producer.
 * 
 * @author Char Sahota
 * @date Oct. 22, 2019
 *
 */
@XmlRootElement
public class WordRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7592966308119269612L;
	
	
	private String text;


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "TextRequest [text=" + text + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		WordRequest other = (WordRequest) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

}
