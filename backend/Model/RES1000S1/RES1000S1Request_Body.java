package model.RES1000S1;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class RES1000S1Request_Body {
	/**
	* 예약 장소
	*/
	private String reserveRoom = "";
	/**
	* 현재 날짜(YYYY-MM-DD)
	*/
	private String Date = "";

	public RES1000S1Request_Body() {
	}

	public RES1000S1Request_Body(JsonNode jsonNode) {
		this.reserveRoom = jsonNode.path("reserveRoom").textValue();
		this.Date = jsonNode.path("Date").textValue();
	}

	@JsonProperty("reserveRoom")
	public String getReserveRoom() {
		return this.reserveRoom;
	}

	public void setReserveRoom(String reserveRoom) {
		this.reserveRoom = reserveRoom;
	}

	@JsonProperty("Date")
	public String getDate() {
		return this.Date;
	}

	public void setDate(String Date) {
		this.Date = Date;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RES1000_1Request_Body [");
		builder.append("reserveRoom=").append(reserveRoom);
		builder.append(", ");
		builder.append("Date=").append(Date);
		builder.append("]");
		return builder.toString();
	}
}