package model.RES1300S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 푸시 예약 리스트
 */
public class RES1300S1Response_Body_trxIdList {
	/**
	* 푸시 예약 알람 일련번호
	*/
	private Integer trxId = null;
	/**
	* 푸시 예약 날짜 (YYYYMMDD)
	*/
	private String trxDate = "";

	public RES1300S1Response_Body_trxIdList() {
	}

	public RES1300S1Response_Body_trxIdList(JsonNode jsonNode) {
		this.trxId = jsonNode.path("trxId").intValue();
		this.trxDate = jsonNode.path("trxDate").textValue();
	}

	@JsonProperty("trxId")
	public Integer getTrxId() {
		return this.trxId;
	}

	public void setTrxId(Integer trxId) {
		this.trxId = trxId;
	}

	@JsonProperty("trxDate")
	public String getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1300S1Response_Body_trxIdList [");
		builder.append("trxId=").append(trxId);
		builder.append(", ");
		builder.append("trxDate=").append(trxDate);
		builder.append("]");
		return builder.toString();
	}
}