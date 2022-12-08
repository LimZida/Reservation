package model.MAN1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 회원 예약 내역 리스트(내가 참석자인)
 */
public class MAN1000S1Response_Body_reserveList2 {
	/**
	* 참석자 수
	*/
	private int attendSize = 0;
	/**
	* 예약 장소
	*/
	private String reserveRoom = "";
	/**
	* 예약 종료 시간
	*/
	private String reserveEnd = "";
	/**
	* 예약 종료 여부
	*/
	private String reserveEndCheck = "";
	/**
	* 예약 시작 시간
	*/
	private String reserveStart = "";
	/**
	* 예약명
	*/
	private String reserveName = "";
	/**
	* 예약 일련번호
	*/
	private int reserveSeqPK = 0;

	public MAN1000S1Response_Body_reserveList2() {
	}

	public MAN1000S1Response_Body_reserveList2(JsonNode jsonNode) {
		this.attendSize = jsonNode.path("attendSize").intValue();
		this.reserveRoom = jsonNode.path("reserveRoom").textValue();
		this.reserveEnd = jsonNode.path("reserveEnd").textValue();
		this.reserveStart = jsonNode.path("reserveStart").textValue();
		this.reserveName = jsonNode.path("reserveName").textValue();
		this.reserveSeqPK = jsonNode.path("reserveSeqPK").intValue();
	}
	

	public String getReserveEndCheck() {
		return reserveEndCheck;
	}
	
	public void setReserveEndCheck(String reserveEndCheck) {
		this.reserveEndCheck = reserveEndCheck;
	}

	@JsonProperty("attendSize")
	public int getAttendSize() {
		return this.attendSize;
	}

	public void setAttendSize(int attendSize) {
		this.attendSize = attendSize;
	}

	@JsonProperty("reserveRoom")
	public String getReserveRoom() {
		return this.reserveRoom;
	}

	public void setReserveRoom(String reserveRoom) {
		this.reserveRoom = reserveRoom;
	}

	@JsonProperty("reserveEnd")
	public String getReserveEnd() {
		return this.reserveEnd;
	}

	public void setReserveEnd(String reserveEnd) {
		this.reserveEnd = reserveEnd;
	}

	@JsonProperty("reserveStart")
	public String getReserveStart() {
		return this.reserveStart;
	}

	public void setReserveStart(String reserveStart) {
		this.reserveStart = reserveStart;
	}

	@JsonProperty("reserveName")
	public String getReserveName() {
		return this.reserveName;
	}

	public void setReserveName(String reserveName) {
		this.reserveName = reserveName;
	}

	@JsonProperty("reserveSeqPK")
	public int getReserveSeqPK() {
		return this.reserveSeqPK;
	}

	public void setReserveSeqPK(int reserveSeqPK) {
		this.reserveSeqPK = reserveSeqPK;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MAN1000S1Response_Body_reserveList2 [");
		builder.append("attendSize=").append(attendSize);
		builder.append(", ");
		builder.append("reserveRoom=").append(reserveRoom);
		builder.append(", ");
		builder.append("reserveEnd=").append(reserveEnd);
		builder.append(", ");
		builder.append("reserveStart=").append(reserveStart);
		builder.append(", ");
		builder.append("reserveName=").append(reserveName);
		builder.append(", ");
		builder.append("reserveSeqPK=").append(reserveSeqPK);
		builder.append("]");
		return builder.toString();
	}
}