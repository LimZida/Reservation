package adapter.demo.model.DM0004;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class DM0004Request_Body {
	/**
	 * 첨부파일목록
	 */
	private List<DM0004Request_Body_attachList> attachList = new ArrayList<DM0004Request_Body_attachList>();
	/**
	 * 제목
	 */
	private String title = "";
	/**
	 * 본문
	 */
	private String contents = "";

	public DM0004Request_Body() {
	}

	public DM0004Request_Body(JsonNode jsonNode) {
		this.attachList = new ArrayList<DM0004Request_Body_attachList>();
		JsonNode attachListNode = jsonNode.path("attachList");
		for (Iterator<JsonNode> iter = attachListNode.iterator(); iter
				.hasNext();) {
			JsonNode node = iter.next();
			attachList.add(new DM0004Request_Body_attachList(node));
		}
		this.title = jsonNode.path("title").textValue();
		this.contents = jsonNode.path("contents").textValue();
	}

	@JsonProperty("attachList")
	public List<DM0004Request_Body_attachList> getAttachList() {
		return this.attachList;
	}

	public void setAttachList(List<DM0004Request_Body_attachList> attachList) {
		this.attachList = attachList;
	}

	@JsonProperty("title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("contents")
	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0004Request_Body [");
		builder.append("attachList=").append(attachList);
		builder.append(", ");
		builder.append("title=").append(title);
		builder.append(", ");
		builder.append("contents=").append(contents);
		builder.append("]");
		return builder.toString();
	}
}