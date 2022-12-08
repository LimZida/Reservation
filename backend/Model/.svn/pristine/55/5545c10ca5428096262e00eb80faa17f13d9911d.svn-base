package model.MAN1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 회원 예약 내역 리스트(내가 참석자인)
 */
public class MAN1000S1Response_Body_reserveList3 {
	/**
	* 참석자 수
	*/
	private int reserveSeqFK = 0;

	public MAN1000S1Response_Body_reserveList3() {
	}

	public MAN1000S1Response_Body_reserveList3(JsonNode jsonNode) {
		this.reserveSeqFK = jsonNode.path("reserveSeqFK").intValue();
	}

	@JsonProperty("reserveSeqFK")
	public int getReserveSeqFK() {
		return this.reserveSeqFK;
	}

	public void setReserveSeqFK(int reserveSeqFK) {
		this.reserveSeqFK = reserveSeqFK;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MAN1000S1Response_Body_reserveList3 [");
		builder.append("reserveSeqFK=").append(reserveSeqFK);
		builder.append("]");
		return builder.toString();
	}
}