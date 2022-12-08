package model.RES1200S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1200S1Response_Body {
	/**
	* 예약 수정 성공 실패 여부
	*/
	private boolean flag = false;
	/**
	* 예약 리스트 목록
	*/
	private List<RES1200S1Response_Body_reserveList> reserveList = new ArrayList<RES1200S1Response_Body_reserveList>();

	public RES1200S1Response_Body() {
	}

	public RES1200S1Response_Body(JsonNode jsonNode) {
		this.reserveList = new ArrayList<RES1200S1Response_Body_reserveList>();
		JsonNode reserveListNode = jsonNode.path("reserveList");
		for (Iterator<JsonNode> iter = reserveListNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			reserveList.add(new RES1200S1Response_Body_reserveList(node));
		}
		this.flag = jsonNode.path("flag").booleanValue();
	}

	@JsonProperty("reserveList")
	public List<RES1200S1Response_Body_reserveList> getReserveList() {
		return this.reserveList;
	}

	public void setReserveList(List<RES1200S1Response_Body_reserveList> reserveList) {
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
		builder.append("RES1200S1Response_Body [");
		builder.append("reserveList=").append(reserveList);
		builder.append(", ");
		builder.append("flag=").append(flag);
		builder.append("]");
		return builder.toString();
	}
}