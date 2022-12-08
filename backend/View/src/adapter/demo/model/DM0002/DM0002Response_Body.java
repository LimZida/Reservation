package adapter.demo.model.DM0002;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class DM0002Response_Body {
	/**
	 * 총게시물 수
	 */
	private Integer totalCnt = null;
	/**
	 * 게시판 목록
	 */
	private List<DM0002Response_Body_list> list = new ArrayList<DM0002Response_Body_list>();

	public DM0002Response_Body() {
	}

	public DM0002Response_Body(JsonNode jsonNode) {
		this.totalCnt = jsonNode.path("totalCnt").intValue();
		this.list = new ArrayList<DM0002Response_Body_list>();
		JsonNode listNode = jsonNode.path("list");
		for (Iterator<JsonNode> iter = listNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			list.add(new DM0002Response_Body_list(node));
		}
	}

	@JsonProperty("totalCnt")
	public Integer getTotalCnt() {
		return this.totalCnt;
	}

	public void setTotalCnt(Integer totalCnt) {
		this.totalCnt = totalCnt;
	}

	@JsonProperty("list")
	public List<DM0002Response_Body_list> getList() {
		return this.list;
	}

	public void setList(List<DM0002Response_Body_list> list) {
		this.list = list;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0002Response_Body [");
		builder.append("totalCnt=").append(totalCnt);
		builder.append(", ");
		builder.append("list=").append(list);
		builder.append("]");
		return builder.toString();
	}
}