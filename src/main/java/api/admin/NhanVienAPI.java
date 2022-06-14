package api.admin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ptithcm.entity.DSTAIKHOAN;
import ptithcm.entity.NHANVIEN;

@RestController(value = "admin")
public class NhanVienAPI  extends service.Order{
	
	public static HashMap<String, String> isNV = new HashMap<String, String>();
	public static String idDelete = "";
	
	@PostMapping("/api/NV")
	public Object insert(@RequestBody HashMap<String, String> data) throws NoSuchAlgorithmException, ParseException
	{
		System.out.print(data.toString());
		String a = InsertNV(data);
		return a;
	}
	
	@PutMapping("/api/NV")
	public Object update(@RequestBody HashMap<String, String> data) throws NoSuchAlgorithmException, ParseException
	{
		System.out.print(data.toString());
		String a = UpdateNV(data);
		return a;
	}
	
	@DeleteMapping("/api/NV")
	public Object delete(@RequestBody String id)
	{
		String a = DeleteNV(id);
		return a;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/api/nv")
	public Object insertNV(@RequestBody HashMap<String, String> data)
	{
		System.out.print(data.toString());
		isNV = data;
		return data;
	}
	
	@PutMapping("/api/nv")
	public Object updateNV(@RequestBody HashMap<String, String> data)
	{
		System.out.print("ok");
		isNV = data;
		return data;
	}
	
	@DeleteMapping("/api/nv")
	public String deletetNV(@RequestBody String id)
	{
		System.out.print(id);
		idDelete = id;
		return id;
	}
	
}
