package admin.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import api.admin.NhanVienAPI;
import ptithcm.controller.LoginController;
import ptithcm.entity.DSTAIKHOAN;
import ptithcm.entity.HOADON;
import ptithcm.entity.NHANVIEN;


@Controller
@Transactional
@RequestMapping("/admin/")
public class NhanVienController extends NhanVienAPI {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "NV", method = RequestMethod.GET)
	public ModelAndView index(ModelMap model,HttpServletRequest request)
	{
		model.addAttribute("tk", LoginController.taikhoan);
		List<NHANVIEN> listNV = this.layAllNV();
		model.addAttribute("listNV", listNV);
		ModelAndView mav = new ModelAndView("admin/view/list-user");
		return mav;
	}
	
	@RequestMapping(value = "NV/insert", method = RequestMethod.GET)
	public ModelAndView insert(@RequestParam(value = "id", required = false) String id,ModelMap model)
	{
		model.addAttribute("tk", LoginController.taikhoan);
		ModelAndView mav = new ModelAndView("admin/view/add-user");
		if(id != null) {
			NHANVIEN temp = this.layNV(id);
			Date da = temp.getNgayvaolam();
			String date = String.valueOf(da);
			mav.addObject("da",date);
			mav.addObject("NV",this.layNV(id));
		}else {
			Integer manv = laymaNVmax();
			NHANVIEN NV = new NHANVIEN();
			if(manv==null)
			{
				NV.setManv(1);
			}else {
				Integer e = manv+1;
				NV.setManv(e);
			}
			mav.addObject("NV", NV);
		}
		return mav;
	}
	
	@RequestMapping(value = "NV/insert1", method = RequestMethod.GET)
	public ModelAndView insert(@ModelAttribute("NV") NHANVIEN NV, BindingResult errors, ModelMap model) throws ParseException, NoSuchAlgorithmException
	{
		model.addAttribute("tk", LoginController.taikhoan);
		HashMap<String, String> data = new HashMap<String, String>(); 
		data = isNV;
		NHANVIEN nv = new NHANVIEN();
		nv.setManv(Integer.valueOf(data.get("manv")));
		nv.setHoten(data.get("hoten"));
		nv.setDiachi(data.get("diachi"));
		nv.setEmail(data.get("email"));
		nv.setHinhanh(data.get("hinhanh"));
		nv.setSdt(data.get("sdt"));
		DSTAIKHOAN temp = new DSTAIKHOAN(data.get("dstaikhoan.taikhoan"), data.get("dstaikhoan.matkhau"), data.get("dstaikhoan.chucvu"));
		nv.setDstaikhoan(temp);
		
		NV = nv;
		
		String da = data.get("ngayvaolam");
		if(da.trim().length()==0)
		{
			model.addAttribute("message", "Date cannot be left blank!");
			ModelAndView mav = new ModelAndView("redirect:/admin/NV/insert");
			return mav;
		}
		if(NV.getDstaikhoan().getTaikhoan().trim().length()==0)
		{
			errors.rejectValue("dstaikhoan.taikhoan", "NV", "Account cannot be left blank!");
			ModelAndView mav = new ModelAndView("redirect:/admin/NV/insert");
			return mav;
		}
		if(NV.getDstaikhoan().getMatkhau().trim().length()==0)
		{
			errors.rejectValue("dstaikhoan.matkhau", "NV", "Password cannot be left blank!");
			ModelAndView mav = new ModelAndView("redirect:/admin/NV/insert");
			return mav;
		}
		if(errors.hasErrors())
		{
			ModelAndView mav = new ModelAndView("redirect:/admin/NV/insert");
			return mav;
		}
		
		List<DSTAIKHOAN> listtk = layAllTK();
		for(DSTAIKHOAN t : listtk)
		{
			
			if(t.getTaikhoan().trim().equals(temp.getTaikhoan().trim()))
			{
				model.addAttribute("message1", "Tên tài khoản đa tồn tại!");
				ModelAndView mav = new ModelAndView("redirect:/admin/NV/insert");
				return mav;
			}
			
		}
		if(temp.getChucvu().trim().equals("Manager"))
		{
			temp.setChucvu("AD");
		}
		else
		{
			temp.setChucvu("NV");
		}
		MessageDigest md = MessageDigest.getInstance("MD5");
		 md.update(temp.getMatkhau().trim().getBytes());
		 byte[] digest = md.digest();
	      String myHash = DatatypeConverter
	                .printHexBinary(digest).toUpperCase();
	      temp.setMatkhau(myHash);
		this.insertTK(temp);
		nv.setDstaikhoan(temp);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String[] time=da.split("-");
		Date date = formatter.parse(time[1]+"/"+time[2]+"/"+time[0]);
		Date d = (Date)date;
		nv.setNgayvaolam(date);
		this.insertNV(nv);
		List<NHANVIEN> list = this.layAllNV();
		model.addAttribute("listNV", list);
		ModelAndView mav = new ModelAndView("redirect:/admin/NV");
		return mav;
	}
	
	@RequestMapping(value = "NV/updated", method = RequestMethod.GET)
	public ModelAndView editNV1(ModelMap model,@ModelAttribute("NV") NHANVIEN NV) throws ParseException, NoSuchAlgorithmException
	{
		model.addAttribute("tk", LoginController.taikhoan);
		HashMap<String, String> data = new HashMap<String, String>(); 
		data = isNV;
		NHANVIEN nv = new NHANVIEN();
		nv.setManv(Integer.valueOf(data.get("manv")));
		nv.setHoten(data.get("hoten"));
		nv.setDiachi(data.get("diachi"));
		nv.setEmail(data.get("email"));
		nv.setHinhanh(data.get("hinhanh"));
		nv.setSdt(data.get("sdt"));
		DSTAIKHOAN temp = new DSTAIKHOAN(data.get("dstaikhoan.taikhoan"), data.get("dstaikhoan.matkhau"), data.get("dstaikhoan.chucvu"));
		nv.setDstaikhoan(temp);
		
		NV = nv;
		
		String da = data.get("ngayvaolam");
		if(temp.getChucvu().trim().equals("Manager"))
		{
			temp.setChucvu("AD");
		}
		else
		{
			temp.setChucvu("NV");
		}
		MessageDigest md = MessageDigest.getInstance("MD5");
		 md.update(temp.getMatkhau().trim().getBytes());
		 byte[] digest = md.digest();
	      String myHash = DatatypeConverter
	                .printHexBinary(digest).toUpperCase();
	      temp.setMatkhau(myHash);
		this.updateTK(temp);
		nv.setDstaikhoan(temp);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String[] time=da.split("-");
		Date date = formatter.parse(time[1]+"/"+time[2]+"/"+time[0]);
		Date d = (Date)date;
		NV.setNgayvaolam(date);
		
		this.updateNV(nv);
		List<NHANVIEN> list = this.layAllNV();
		model.addAttribute("listNV", list);
		ModelAndView mav = new ModelAndView("redirect:/admin/NV");
		return mav;
	}
	
	@RequestMapping(value = "NV/delete", method = RequestMethod.GET)
	public ModelAndView deleteNV(ModelMap model)
	{	
		model.addAttribute("tk", LoginController.taikhoan);
		String id= idDelete;
		NHANVIEN t = this.layNV(id);
		Collection<HOADON> hd = t.getHoadonlist();
		if(!(hd.isEmpty()))
		{
			model.addAttribute("message", "Không thể xóa nhân viên đã lập hóa đơn!");
			List<NHANVIEN> listNV = this.layAllNV();
			model.addAttribute("listNV", listNV);
			ModelAndView mav = new ModelAndView("admin/view/list-user");
			return mav;
		}
		else {
			deleteTK(id);
			this.deleteNV(id);
			List<NHANVIEN> listNV = this.layAllNV();
			model.addAttribute("listNV", listNV);
			ModelAndView mav = new ModelAndView("redirect:/admin/NV.htm");
			return mav;
		}
	}
	
	
	public List<DSTAIKHOAN> layAllTK()
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM DSTAIKHOAN";
		Query query = session.createQuery(hql);
		List<DSTAIKHOAN> list = query.list();
		return list;
	}
	
	public List<NHANVIEN> layAllNV()
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM NHANVIEN";
		Query query = session.createQuery(hql);
		List<NHANVIEN> list = query.list();
		return list;
	}
	
	public Integer laymaNVmax()
	{
		Session session = factory.getCurrentSession();
		String hql = "SELECT MAX(A.manv) FROM NHANVIEN A";
		Query query = session.createQuery(hql);
		List<Integer> list = query.list();
		Integer manv = list.get(0);
		return manv;
	}
	
	public NHANVIEN layNV(String id)
	{
		id.trim();
		Session session = factory.getCurrentSession();
		String hql = "FROM NHANVIEN where MANV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		NHANVIEN nv = (NHANVIEN)query.list().get(0);
		return nv;
	}
	
	public void insertTK(DSTAIKHOAN tk)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.save(tk);
			t.commit();
		}catch(Exception e){
			t.rollback();
		}
		finally {
			session.close();
		}
	}
	
	public void insertNV(NHANVIEN nv)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.save(nv);
			t.commit();
		}catch(Exception e){
			t.rollback();
		}
		finally {
			session.close();
		}
	}
	
	public void updateTK(DSTAIKHOAN tk)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.update(tk);
			t.commit();
		}catch(Exception e){
			t.rollback();
		}
		finally {
			session.close();
		}
	}
	
	public void updateNV(NHANVIEN nv)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.update(nv);
			t.commit();
		}catch(Exception e){
			t.rollback();
		}
		finally {
			session.close();
		}
	}
	
	public void deleteTK(String id)
	{
		id=id.trim();
		Session session = factory.getCurrentSession();
		String hql = "FROM NHANVIEN where MANV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		NHANVIEN nv = (NHANVIEN)query.list().get(0);
		String tk = nv.getDstaikhoan().getTaikhoan();
		session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql1 ="DELETE FROM DSTAIKHOAN "  + 
	             "WHERE TAIKHOAN = :tk";
		Query query1 = session.createQuery(hql1);
		query1.setParameter("tk", tk);
		int rs = query1.executeUpdate();
		t.commit();

	}
	
	public void deleteNV(String id)
	{
		id=id.trim();
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql ="DELETE FROM NHANVIEN "  + 
	             "WHERE MANV = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		int rs = query.executeUpdate();
		t.commit();

	}
}
