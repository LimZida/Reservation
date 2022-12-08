package adapter.demo.model.DM0003;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class DM0003Response_Body {
	/**
	 * 등록일시(yyyyMMddHHmm)
	 */
	private String regDate = "";
	/**
	 * 첨부파일 여부(Y/N)
	 */
	private String attachFlag = "";
	/**
	 * 본문
	 */
	private String contents = "";
	/**
	 * 부서명
	 */
	private String deptName = "";
	/**
	 * 첨부파일 목록
	 */
	private List<DM0003Response_Body_attachList> attachList = new ArrayList<DM0003Response_Body_attachList>();
	/**
	 * 제목
	 */
	private String title = "";
	/**
	 * 등록자명
	 */
	private String regName = "";
	/**
	 * 첨부파일 다운로드 URL
	 */
	private String downloadUrl = "";

	public DM0003Response_Body() {
	}

	public DM0003Response_Body(JsonNode jsonNode) {
		this.regDate = jsonNode.path("regDate").textValue();
		this.attachFlag = jsonNode.path("attachFlag").textValue();
		this.contents = jsonNode.path("contents").textValue();
		this.deptName = jsonNode.path("deptName").textValue();
		this.attachList = new ArrayList<DM0003Response_Body_attachList>();
		JsonNode attachListNode = jsonNode.path("attachList");
		for (Iterator<JsonNode> iter = attachListNode.iterator(); iter
				.hasNext();) {
			JsonNode node = iter.next();
			attachList.add(new DM0003Response_Body_attachList(node));
		}
		this.title = jsonNode.path("title").textValue();
		this.regName = jsonNode.path("regName").textValue();
		this.downloadUrl = jsonNode.path("downloadUrl").textValue();
	}

	@JsonProperty("regDate")
	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@JsonProperty("attachFlag")
	public String getAttachFlag() {
		return this.attachFlag;
	}

	public void setAttachFlag(String attachFlag) {
		this.attachFlag = attachFlag;
	}

	@JsonProperty("contents")
	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@JsonProperty("deptName")
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@JsonProperty("attachList")
	public List<DM0003Response_Body_attachList> getAttachList() {
		return this.attachList;
	}

	public void setAttachList(List<DM0003Response_Body_attachList> attachList) {
		this.attachList = attachList;
	}

	@JsonProperty("title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonProperty("regName")
	public String getRegName() {
		return this.regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	@JsonProperty("downloadUrl")
	public String getDownloadUrl() {
		return this.downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0003Response_Body [");
		builder.append("regDate=").append(regDate);
		builder.append(", ");
		builder.append("attachFlag=").append(attachFlag);
		builder.append(", ");
		builder.append("contents=").append(contents);
		builder.append(", ");
		builder.append("deptName=").append(deptName);
		builder.append(", ");
		builder.append("attachList=").append(attachList);
		builder.append(", ");
		builder.append("title=").append(title);
		builder.append(", ");
		builder.append("regName=").append(regName);
		builder.append(", ");
		builder.append("downloadUrl=").append(downloadUrl);
		builder.append("]");
		return builder.toString();
	}
}