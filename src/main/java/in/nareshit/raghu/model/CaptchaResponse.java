package in.nareshit.raghu.model;

import lombok.Data;

@Data
public class CaptchaResponse {
	private boolean success;
	private String challege_ts;
	private String hostname;
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getChallege_ts() {
		return challege_ts;
	}
	public void setChallege_ts(String challege_ts) {
		this.challege_ts = challege_ts;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
}
