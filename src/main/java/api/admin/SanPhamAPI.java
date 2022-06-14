package api.admin;

import java.util.HashMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import service.Order;

@RestController(value = "staff")
public class SanPhamAPI  extends Order{
	
	public static HashMap<String, String> isSP = new HashMap<String, String>();
	public static String idSPDelete = "";

	@PostMapping("/api/sp")
	public Object insertSP(@RequestBody HashMap<String, String> data)
	{
		System.out.print(data.toString());
		isSP = data;
		return data;
	}
	
	@PutMapping("/api/sp")
	public Object updateSP(@RequestBody HashMap<String, String> data)
	{
		System.out.print("ok");
		isSP = data;
		return data;
	}
	
	@DeleteMapping("/api/sp")
	public String deletetSP(@RequestBody String id)
	{
		System.out.print(id);
		idSPDelete = id;
		return id;
	}
	
	@PostMapping("/api/order")
	public String confirm(@RequestBody String id)
	{
		String a = Confirm(id);
		System.out.print(a);
		return a;
	}
}
