package model.MYP1000S1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Comment
 */
public class MYP1000S1Request_Body {
	/**
	* 시작 인덱스
	*/
	private Integer startIndex = null;
	/**
	* 종료 인덱스
	*/
	private Integer endIndex = null;
	/**
	* 시작,예정,종료 여부 배열
	*/
	private List<Integer> checkResultArray = new ArrayList<Integer>();
	/**
	* 사용자 시퀀스
	*/
	private Integer userSeqPK = null;

	public MYP1000S1Request_Body() {
	}

	public MYP1000S1Request_Body(JsonNode jsonNode) {
		this.startIndex = jsonNode.path("startIndex").intValue();
		this.endIndex = jsonNode.path("endIndex").intValue();
		this.checkResultArray = new ArrayList<Integer>();
		JsonNode checkResultArrayNode = jsonNode.path("checkResultArray");
		for (Iterator<JsonNode> iter = checkResultArrayNode.iterator(); iter.hasNext();) {
			JsonNode node = iter.next();
			checkResultArray.add(node.intValue());
		}
		this.userSeqPK = jsonNode.path("userSeqPK").intValue();
	}

	@JsonProperty("startIndex")
	public Integer getStartIndex() {
		return this.startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	@JsonProperty("endIndex")
	public Integer getEndIndex() {
		return this.endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	@JsonProperty("checkResultArray")
	public List<Integer> getCheckResultArray() {
		return this.checkResultArray;
	}

	public void setCheckResultArray(List<Integer> checkResultArray) {
		this.checkResultArray = checkResultArray;
	}

	@JsonProperty("userSeqPK")
	public Integer getUserSeqPK() {
		return this.userSeqPK;
	}

	public void setUserSeqPK(Integer userSeqPK) {
		this.userSeqPK = userSeqPK;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MYP1000S1Request_Body [");
		builder.append("startIndex=").append(startIndex);
		builder.append(", ");
		builder.append("endIndex=").append(endIndex);
		builder.append(", ");
		builder.append("checkResultArray=").append(checkResultArray);
		builder.append(", ");
		builder.append("userSeqPK=").append(userSeqPK);
		builder.append("]");
		return builder.toString();
	}
}