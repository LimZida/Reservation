package model.MAN1000S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class MAN1000S1Response_Body {
	/**
	* 회원 이름
	*/
	private String userName = "";
	/**
	 * 예약 일련번호(응답으로 미사용)
	 */
	private int reserveSeqFK = 0;
	/**
	* 회원 예약 내역 리스트(내가 예약자인)
	*/
	private List<MAN1000S1Response_Body_reserveList1> reserveList1 = new ArrayList<MAN1000S1Response_Body_reserveList1>();
	/**
	* 회원 예약 내역 리스트(내가 참석자인)
	*/
	private List<MAN1000S1Response_Body_reserveList2> reserveList2 = new ArrayList<MAN1000S1Response_Body_reserveList2>();

	public MAN1000S1Response_Body() {
	}

	public MAN1000S1Response_Body(JsonNode jsonNode) {
		this.userName = jsonNode.path("userName").textValue();
		this.reserveList1 = new ArrayList<MAN1000S1Response_Body_reserveList1>();
		JsonNode reserveList1Node = jsonNode.path("reserveList1");
		for (Iterator<JsonNode> iter = reserveList1Node.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			reserveList1.add(new MAN1000S1Response_Body_reserveList1(node));
		}
		this.reserveList2 = new ArrayList<MAN1000S1Response_Body_reserveList2>();
		JsonNode reserveList2Node = jsonNode.path("reserveList2");
		for (Iterator<JsonNode> iter = reserveList2Node.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			reserveList2.add(new MAN1000S1Response_Body_reserveList2(node));
		}
	}

	@JsonProperty("userName")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonProperty("reserveList1")
	public List<MAN1000S1Response_Body_reserveList1> getReserveList1() {
		return this.reserveList1;
	}

	public void setReserveList1(List<MAN1000S1Response_Body_reserveList1> reserveList1) {
		this.reserveList1 = reserveList1;
	}

	@JsonProperty("reserveList2")
	public List<MAN1000S1Response_Body_reserveList2> getReserveList2() {
		return this.reserveList2;
	}

	public void setReserveList2(List<MAN1000S1Response_Body_reserveList2> reserveList2) {
		this.reserveList2 = reserveList2;
	}

	public int getReserveSeqFK() {
		return reserveSeqFK;
	}

	public void setReserveSeqFK(int reserveSeqFK) {
		this.reserveSeqFK = reserveSeqFK;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MAN1000S1Response_Body [");
		builder.append("userName=").append(userName);
		builder.append(", ");
		builder.append("reserveList1=").append(reserveList1);
		builder.append(", ");
		builder.append("reserveList2=").append(reserveList2);
		builder.append("]");
		return builder.toString();
	}

}