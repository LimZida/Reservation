package model.RES1000S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1000S1Response_Body {
	/**
	* 예약 내역 리스트
	*/
	private List<RES1000S1Response_Body_reserveList> reserveList = new ArrayList<RES1000S1Response_Body_reserveList>();

	public RES1000S1Response_Body() {
	}

	public RES1000S1Response_Body(JsonNode jsonNode) {
		this.reserveList = new ArrayList<RES1000S1Response_Body_reserveList>();
		JsonNode reserveListNode = jsonNode.path("reserveList");
		for (Iterator<JsonNode> iter = reserveListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			reserveList.add(new RES1000S1Response_Body_reserveList(node));
		}
	}

	@JsonProperty("reserveList")
	public List<RES1000S1Response_Body_reserveList> getReserveList() {
		return this.reserveList;
	}

	public void setReserveList(List<RES1000S1Response_Body_reserveList> reserveList) {
		this.reserveList = reserveList;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1000S1Response_Body [");
		builder.append("reserveList=").append(reserveList);
		builder.append("]");
		return builder.toString();
	}
}