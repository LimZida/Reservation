package model.RES1310S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1310S1Request_Body {
	/**
	* 회원 일련번호
	*/
	private int userSeqPK = 0;
	/**
	* 예약 일련번호
	*/
	private int reserveSeqPK = 0;
	/**
	* 회의 종료 시간
	*/
	private String reserveEnd = "";
	/**
	* 회의실
	*/
	private String reserveRoom = "";
	/**
	* 회의 날짜
	*/
	private String reserveDate = "";

	public RES1310S1Request_Body() {
	}

	public RES1310S1Request_Body(JsonNode jsonNode) {
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
		this.reserveSeqPK = jsonNode.path("reserveSeqPK").intValue();
		this.reserveEnd = jsonNode.path("reserveEnd").textValue();
		this.reserveRoom = jsonNode.path("reserveRoom").textValue();
		this.reserveDate = jsonNode.path("reserveDate").textValue();
	}

	@JsonProperty("userSeqPK")
	public int getUserSeqPK() {
		return this.userSeqPK;
	}

	public void setUserSeqPK(int userSeqPK) {
		this.userSeqPK = userSeqPK;
	}

	@JsonProperty("reserveSeqPK")
	public int getReserveSeqPK() {
		return this.reserveSeqPK;
	}

	public void setReserveSeqPK(int reserveSeqPK) {
		this.reserveSeqPK = reserveSeqPK;
	}

	@JsonProperty("reserveEnd")
	public String getReserveEnd() {
		return this.reserveEnd;
	}

	public void setReserveEnd(String reserveEnd) {
		this.reserveEnd = reserveEnd;
	}

	@JsonProperty("reserveRoom")
	public String getReserveRoom() {
		return this.reserveRoom;
	}

	public void setReserveRoom(String reserveRoom) {
		this.reserveRoom = reserveRoom;
	}

	@JsonProperty("reserveDate")
	public String getReserveDate() {
		return this.reserveDate;
	}

	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1310S1Request_Body [");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append(", ");
		builder.append("reserveSeqPK=").append(reserveSeqPK);
		builder.append(", ");
		builder.append("reserveEnd=").append(reserveEnd);
		builder.append(", ");
		builder.append("reserveRoom=").append(reserveRoom);
		builder.append(", ");
		builder.append("reserveDate=").append(reserveDate);
		builder.append("]");
		return builder.toString();
	}
}