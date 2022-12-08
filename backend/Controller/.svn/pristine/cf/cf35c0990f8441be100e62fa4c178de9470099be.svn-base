package service;


import java.util.List;

import org.springframework.stereotype.Component;

import model.MYP1000S1.MYP1000S1Response_Body_reserveList;
/***
*
* <p> Title : SliceArrayService
*
*
* <p> Description : getSlice(List<MYP1000S1Response_Body_reserveList> slicedResult,Integer startIndex, Integer endIndex)
* 					=>요청으로 받은 페이지에 맞게 배열을 잘라주는 함수
*
*
*
* <p> Last Update Date : 2022.11.14
*
*
* @author LHY
*
*/

@Component
public class SliceArrayService {
//	slicedReuslt를 (startIdx,endIdx) 범위로 slice한다
	public List<MYP1000S1Response_Body_reserveList> getSlice(List<MYP1000S1Response_Body_reserveList> slicedResult,Integer startIndex, Integer endIndex){
//		맨 처음부터 List가 endIndex보다 부족할 경우
//		ex) slicedResult=[1,2,3,4,5]  sIdx=0 , eIdx=20
		if(slicedResult.size()<endIndex) {
			endIndex=slicedResult.size();
//		다시 통신이 들어올 경우
//		ex) slicedResult=[1,2,3,4,5]  sIdx=0 , eIdx=5
//									  sIdx=5 , eIdx=10
			if(slicedResult.size()-1<startIndex) {
				startIndex=slicedResult.size()-1;
				endIndex=slicedResult.size();
			}
		}
		return slicedResult.subList(startIndex, endIndex);
	}
}
