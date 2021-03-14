package br.com.stoom.ms.adress.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
	private String response;

	public Response(String response) {
		super();
		this.response = response;
	}
}
