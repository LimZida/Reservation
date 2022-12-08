package model.header;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class DMHeader {
	/**
	* 에러코드
	*/
	private String error_code = "";
	/**
	* 에러메시지
	*/
	private String error_text = "";
	/**
	* 추가정보
	*/
	private String info_text = "";
	/**
	* 데이터 암복호화 여부
	*/
	private boolean is_cryption = false;
	/**
	* hybrid web 세션 id
	*/
	private String login_session_id = "";
	/**
	* 전문 버전
	*/
	private String message_version = "";
	/**
	* 메시지처리결과
	*/
	private boolean result = false;
	/**
	* 전문코드
	*/
	private String trcode = "";

	public DMHeader() {
	}

	public DMHeader(JsonNode jsonNode) {
		this.error_code = jsonNode.path("error_code").textValue();
		this.error_text = jsonNode.path("error_text").textValue();
		this.info_text = jsonNode.path("info_text").textValue();
		this.is_cryption = jsonNode.path("is_cryption").booleanValue();
		this.login_session_id = jsonNode.path("login_session_id").textValue();
		this.message_version = jsonNode.path("message_version").textValue();
		this.result = jsonNode.path("result").booleanValue();
		this.trcode = jsonNode.path("trcode").textValue();
	}

	@JsonProperty("error_code")
	public String getError_code() {
		return this.error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	@JsonProperty("error_text")
	public String getError_text() {
		return this.error_text;
	}

	public void setError_text(String error_text) {
		this.error_text = error_text;
	}

	@JsonProperty("info_text")
	public String getInfo_text() {
		return this.info_text;
	}

	public void setInfo_text(String info_text) {
		this.info_text = info_text;
	}

	@JsonProperty("is_cryption")
	public boolean getIs_cryption() {
		return this.is_cryption;
	}

	public void setIs_cryption(boolean is_cryption) {
		this.is_cryption = is_cryption;
	}

	@JsonProperty("login_session_id")
	public String getLogin_session_id() {
		return this.login_session_id;
	}

	public void setLogin_session_id(String login_session_id) {
		this.login_session_id = login_session_id;
	}

	@JsonProperty("message_version")
	public String getMessage_version() {
		return this.message_version;
	}

	public void setMessage_version(String message_version) {
		this.message_version = message_version;
	}

	@JsonProperty("result")
	public boolean getResult() {
		return this.result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	@JsonProperty("trcode")
	public String getTrcode() {
		return this.trcode;
	}

	public void setTrcode(String trcode) {
		this.trcode = trcode;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DMHeader [");
		builder.append("error_code=").append(error_code);
		builder.append(", ");
		builder.append("error_text=").append(error_text);
		builder.append(", ");
		builder.append("info_text=").append(info_text);
		builder.append(", ");
		builder.append("is_cryption=").append(is_cryption);
		builder.append(", ");
		builder.append("login_session_id=").append(login_session_id);
		builder.append(", ");
		builder.append("message_version=").append(message_version);
		builder.append(", ");
		builder.append("result=").append(result);
		builder.append(", ");
		builder.append("trcode=").append(trcode);
		builder.append("]");
		return builder.toString();
	}
}