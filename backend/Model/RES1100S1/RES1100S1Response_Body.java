package model.RES1100S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1100S1Response_Body {
	/**
	* 예약 일련번호
	*/
	private int reserveSeqPK = 0;
	/**
	* 예약 리스트 목록
	*/
	private List<RES1100S1Response_Body_reserveList> reserveList = new ArrayList<RES1100S1Response_Body_reserveList>();
	/**
	* 예약 성공 실패 여부
	*/
	private boolean flag = false;

	public RES1100S1Response_Body() {
	}

	public RES1100S1Response_Body(JsonNode jsonNode) {
		this.reserveList = new ArrayList<RES1100S1Response_Body_reserveList>();
		JsonNode reserveListNode = jsonNode.path("reserveList");
		for (Iterator<JsonNode> iter = reserveListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			reserveList.add(new RES1100S1Response_Body_reserveList(node));
		}
		this.reserveSeqPK = jsonNode.path("reserveSeqPK").intValue();
		this.flag = jsonNode.path("flag").booleanValue();
	}

	@JsonProperty("reserveSeqPK")
	public int getReserveSeqPK() {
		return this.reserveSeqPK;
	}

	public void setReserveSeqPK(int reserveSeqPK) {
		this.reserveSeqPK = reserveSeqPK;
	}
	
	@JsonProperty("reserveList")
	public List<RES1100S1Response_Body_reserveList> getReserveList() {
		return this.reserveList;
	}

	public void setReserveList(List<RES1100S1Response_Body_reserveList> reserveList) {
		this.reserveList = reserveList;
	}

	@JsonProperty("flag")
	public boolean getFlag() {
		return this.flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1100S1Response_Body [");
		builder.append("reserveSeqPK=").append(reserveSeqPK);
		builder.append(", ");
		builder.append("reserveList=").append(reserveList);
		builder.append(", ");
		builder.append("flag=").append(flag);
		builder.append("]");
		return builder.toString();
	}
}