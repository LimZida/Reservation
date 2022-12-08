package service;

import org.springframework.stereotype.Component;
/***
*
* <p> Title : TimeChangeService
*
*
* <p> Description : makeTimeChange(String time,String canceledTime)
* 					=>종료 시에 가장 가까운 시간대로 시간을 변경해주는 함수
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
public class TimeChangeService {

	public String makeTimeChange(String time,String canceledTime) {

//		시간 받아와 문자열 ":" 기준으로 split
//		ex) "12:00" -> ["12","00"]
//		기존 DB에 존재하는 종료시간
		String[] originalTime=time.split(":");
		String originalHour=originalTime[0];
		String originalMinute=originalTime[1];

//		요청받은 종료시간
		String[] splitedTime=canceledTime.split(":");
		String hour=splitedTime[0];
		String minute=splitedTime[1];


//		쪼갠 문자열을 정수로 변환
//		요청받은 종료시간
		int minuteHour=Integer.parseInt(hour);
		int minuteInt=Integer.parseInt(minute);
//		기존 종료시간
		int originMinuteHour=Integer.parseInt(originalHour);
		int originMinuteInt=Integer.parseInt(originalMinute);

//		기존 종료시간보다 요청 종료시간이 더 큰 경우 2가지
//		기존 종료시간 반환
		if(originMinuteHour<minuteHour) {
			return time;
		}
		else if(originMinuteHour==minuteHour && originMinuteInt<=minuteInt) {
			return time;
		}


//		01분부터 29분까지는 30분으로 처리
//		ex) 12:02 => 12:30
		if(minuteInt<=29&&minuteInt>=1) {
			splitedTime[1]="30";
		}
//		31분부터 59분까지는 시+1,00분으로 처리
//		ex) 12:32 => 13:00
		else if(minuteInt>=31&&minuteInt<=59){
			minuteHour++;
			splitedTime[0]=Integer.toString(minuteHour);
			splitedTime[1]="00";
		}
//		30분, 00분의 경우에는 매개변수로 받은 값 그대로 return
//		ex) 12:00 , 12:30
		else {
			return time;
		}

//		변환된 시간과 분을 ":"과 함께 다시 String으로 합친 후 return
//		ex) changedTime = "변환된 시간" + ":" + "변환된 분";
		String changedTime=splitedTime[0]+":"+splitedTime[1];
		return changedTime;
	}
}
