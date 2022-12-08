package adapter.demo.model.DM0002;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 게시판 목록
 */
public class DM0002Response_Body_list {
	/**
	 * 문서 ID
	 */
	private String docId = "";
	/**
	 * 제목
	 */
	private String title = "";
	/**
	 * 등록일시(yyyyMMddhHmm)
	 */
	private String regDate = "";
	/**
	 * 첨부파일 여부(Y/N)
	 */
	private String attachFlag = "";
	/**
	 * 등록자명
	 */
	private String regName = "";
	/**
	 * 부서명
	 */
	private String deptName = "";

	public DM0002Response_Body_list() {
	}

	public DM0002Response_Body_list(JsonNode jsonNode) {
		this.docId = jsonNode.path("docId").textValue();
		this.title = jsonNode.path("title").textValue();
		this.regDate = jsonNode.path("regDate").textValue();
		this.attachFlag = jsonNode.path("attachFlag").textValue();
		this.regName = jsonNode.path("regName").textValue();
		this.deptName = jsonNode.path("deptName").textValue();
	}

	@JsonProperty("docId")
	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	@JsonProperty("title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@JsonProperty("regName")
	public String getRegName() {
		return this.regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	@JsonProperty("deptName")
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DM0002Response_Body_list [");
		builder.append("docId=").append(docId);
		builder.append(", ");
		builder.append("title=").append(title);
		builder.append(", ");
		builder.append("regDate=").append(regDate);
		builder.append(", ");
		builder.append("attachFlag=").append(attachFlag);
		builder.append(", ");
		builder.append("regName=").append(regName);
		builder.append(", ");
		builder.append("deptName=").append(deptName);
		builder.append("]");
		return builder.toString();
	}
}