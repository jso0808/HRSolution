package attendance;

import java.sql.SQLException;
import java.util.List;

public interface AttendanceDAO {
	public int insertAttendance(AttendanceDTO dto) throws SQLException;
	public int updateAttendance(AttendanceDTO dto) throws SQLException;
	public int deleteAttendance(String attNo) throws SQLException;
	public List<AttendanceDTO> listAttendance(String date);
	public List<AttendanceDTO> readAttendacne(String id, String date);
	public int readWorkinghours(String id, String date) throws SQLException;;
	
}

