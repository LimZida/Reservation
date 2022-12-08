package model.RES1320S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1320S1Response_Body {
	/**
	* 종료 성공 실패 여부
	*/
	private boolean Flag = false;
	/**
	* 예약 종료 컬럼 내용 반환
	*/
	private String reserveEndCheck = "";
	/**
	 * 예약 종료 시간
	 */
	private String reserveEnd = "";

	public RES1320S1Response_Body() {
	}

	public RES1320S1Response_Body(JsonNode jsonNode) {
		this.Flag = jsonNode.path("Flag").booleanValue();
		this.reserveEndCheck = jsonNode.path("reserveEndCheck").textValue();
	}

	@JsonProperty("Flag")
	public boolean getFlag() {
		return this.Flag;
	}

	public void setFlag(boolean Flag) {
		this.Flag = Flag;
	}

	@JsonProperty("reserveEndCheck")
	public String getReserveEndCheck() {
		return this.reserveEndCheck;
	}

	public void setReserveEndCheck(String reserveEndCheck) {
		this.reserveEndCheck = reserveEndCheck;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1320S1Response_Body [");
		builder.append("Flag=").append(Flag);
		builder.append(", ");
		builder.append("reserveEndCheck=").append(reserveEndCheck);
		builder.append("]");
		return builder.toString();
	}

	public String getReserveEnd() {
		return reserveEnd;
	}

	public void setReserveEnd(String reserveEnd) {
		this.reserveEnd = reserveEnd;
	}
}