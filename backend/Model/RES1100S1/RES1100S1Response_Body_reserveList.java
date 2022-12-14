package model.RES1100S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 시간 대 존재하는 회의
 */
public class RES1100S1Response_Body_reserveList {
	/**
	 * 예약 시작 시간
	 */
	private String reserveStart = "";
	/**
	 * 예약 종료 시간
	 */
	private String reserveEnd = "";

	public RES1100S1Response_Body_reserveList() {
	}

	public RES1100S1Response_Body_reserveList(JsonNode jsonNode) {
		this.reserveStart = jsonNode.path("reserveStart").textValue();
		this.reserveEnd = jsonNode.path("reserveEnd").textValue();
	}

	@JsonProperty("reserveStart")
	public String getReserveStart() {
		return this.reserveStart;
	}

	public void setReserveStart(String reserveStart) {
		this.reserveStart = reserveStart;
	}

	@JsonProperty("reserveEnd")
	public String getReserveEnd() {
		return this.reserveEnd;
	}

	public void setReserveEnd(String reserveEnd) {
		this.reserveEnd = reserveEnd;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1110S1Response_Body_reserveList [");
		builder.append("reserveStart=").append(reserveStart);
		builder.append(", ");
		builder.append("reserveEnd=").append(reserveEnd);
		builder.append("]");
		return builder.toString();
	}
}