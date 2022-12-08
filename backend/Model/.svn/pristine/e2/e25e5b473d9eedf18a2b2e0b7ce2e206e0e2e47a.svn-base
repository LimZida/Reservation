package model.RES1300S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1300S1Request_Body {
	/**
	* 예약 일련번호
	*/
	private Integer reserveSeqPK = null;

	public RES1300S1Request_Body() {
	}

	public RES1300S1Request_Body(JsonNode jsonNode) {
		this.reserveSeqPK = jsonNode.path("reserveSeqPK").intValue();
	}

	@JsonProperty("reserveSeqPK")
	public Integer getReserveSeqPK() {
		return this.reserveSeqPK;
	}

	public void setReserveSeqPK(Integer reserveSeqPK) {
		this.reserveSeqPK = reserveSeqPK;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1300S1Request_Body [");
		builder.append("reserveSeqPK=").append(reserveSeqPK);
		builder.append("]");
		return builder.toString();
	}
}