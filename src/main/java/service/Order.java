package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import ptithcm.controller.LoginController;
import ptithcm.entity.CTDDH;
import ptithcm.entity.DONDATHANG;
import ptithcm.entity.DSTAIKHOAN;
import ptithcm.entity.HOADON;
import ptithcm.entity.NHANVIEN;

@Transactional
public class Order {
	@Autowired
	SessionFactory factory;
    
	public String Confirm(String id)
	{
		try {
			id=id.trim();
			HOADON hd=new HOADON();
			Session session = factory.getCurrentSession();
			String hql = "UPDATE DONDATHANG set trangthai=:success WHERE msddh=:idh";
			Query query = session.createQuery(hql);
			query.setParameter("success", "CONFIRMED");
			query.setParameter("idh", id);
			int result=query.executeUpdate();
			String hqlDH = "FROM DONDATHANG Y WHERE msddh=:ms";
			Query queryDH= session.createQuery(hqlDH);
			queryDH.setParameter("ms",id);
			List<DONDATHANG> list2 = queryDH.list();
			DONDATHANG dh=list2.get(0);

			hd.setSohd(taomaHD());
			System.out.println(hd.getSohd());
			Date dNow=new Date();
			hd.setNgaylaphd(dNow);

			hd.setNhanvien(null);
			hd.setDondathang(dh);
			Float Tong=0f;
			String listdh="";
			 for (CTDDH e: hd.getDondathang().getCtddhlist() ) 
			 {
				 listdh=listdh+"<br>"+e.getSanpham().getTensp()+"-Số lượng: "+e.getSL();
				 Integer slt=e.getSanpham().getSoluongton()-e.getSL();
				 if(slt>=0) {
					 
					 if(e.getSanpham().getSale()!=0)
					 {
						 Tong=Tong+e.getSL()*e.getSanpham().getDongia()*(1-e.getSanpham().getSale());
					 }
					 else
					 {
						 Tong=Tong+e.getSL()*e.getSanpham().getDongia();
					 }
				 }
				 else {
						return "error";
				 }
				 
			 }
			 for (CTDDH e: hd.getDondathang().getCtddhlist() ) {
				 Integer slt=e.getSanpham().getSoluongton()-e.getSL();
				 e.getSanpham().setSoluongton(slt);
			 }
			 Tong=(float) Math.round(Tong*100.0/100.0);
			hd.setTonggia(Tong);
			dh.getKhachhang().setDoanhso(dh.getKhachhang().getDoanhso()+Tong);		
			session.save(hd);
			Calendar c = Calendar.getInstance();
			c.setTime(dNow);
			c.add(Calendar.DATE,4);
			 Date currentDatePlusOne = c.getTime();
			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 String strDate=formatter.format(currentDatePlusOne);
			
		}
		catch(Exception e){
			return "error";
		}
		return "success";
	}
	
	public String InsertNV(HashMap<String, String> data) throws NoSuchAlgorithmException, ParseException
	{
		NHANVIEN nv = new NHANVIEN();
		nv.setManv(Integer.valueOf(data.get("manv")));
		nv.setHoten(data.get("hoten"));
		nv.setDiachi(data.get("diachi"));
		nv.setEmail(data.get("email"));
		nv.setHinhanh(data.get("hinhanh"));
		nv.setSdt(data.get("sdt"));
		DSTAIKHOAN temp = new DSTAIKHOAN(data.get("dstaikhoan.taikhoan"), data.get("dstaikhoan.matkhau"), data.get("dstaikhoan.chucvu"));
		nv.setDstaikhoan(temp);
		
		String da = data.get("ngayvaolam");
	
		
		
		List<DSTAIKHOAN> listtk = layAllTK();
		for(DSTAIKHOAN t : listtk)
		{
			
			if(t.getTaikhoan().trim().equals(temp.getTaikhoan().trim()))
			{
				return "tk already exists";
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
		this.insertTK1(temp);
		nv.setDstaikhoan(temp);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String[] time=da.split("-");
		Date date = formatter.parse(time[1]+"/"+time[2]+"/"+time[0]);
		Date d = (Date)date;
		nv.setNgayvaolam(date);
		if(this.insertNV1(nv) == true) {
			return "success";
		}else {
			return "error";
		}
	}
	
	public String UpdateNV(HashMap<String, String> data) throws NoSuchAlgorithmException, ParseException {
		NHANVIEN nv = new NHANVIEN();
		nv.setManv(Integer.valueOf(data.get("manv")));
		nv.setHoten(data.get("hoten"));
		nv.setDiachi(data.get("diachi"));
		nv.setEmail(data.get("email"));
		nv.setHinhanh(data.get("hinhanh"));
		nv.setSdt(data.get("sdt"));
		DSTAIKHOAN temp = new DSTAIKHOAN(data.get("dstaikhoan.taikhoan"), data.get("dstaikhoan.matkhau"), data.get("dstaikhoan.chucvu"));
		nv.setDstaikhoan(temp);
		
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
		this.updateTK1(temp);
		nv.setDstaikhoan(temp);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String[] time=da.split("-");
		Date date = formatter.parse(time[1]+"/"+time[2]+"/"+time[0]);
		Date d = (Date)date;
		nv.setNgayvaolam(date);
		
		if(this.updateNV1(nv)==true) {
			return "success";
		}else {
			return "error";
		}
	}
	
	public String DeleteNV(String id) {
		NHANVIEN t = this.layNV(id);
		Collection<HOADON> hd = t.getHoadonlist();
		if(!(hd.isEmpty()))
		{
			
			return "Không thể xóa nhân viên đã lập hóa đơn";
		}
		else {
			deleteTK(id);
			this.deleteNV(id);
			return "success";
		}
	}
	
	public Integer laymaHD()
	{
		Session session = factory.getCurrentSession();
		String hql = "select max( CAST(sohd AS int)) from HOADON ";
		Query query = session.createQuery(hql);
		List<Integer> list = query.list();
		Integer mahd=list.get(0);
		return mahd;
	}
	
	public String taomaHD()
	{
		Integer ma=laymaHD();
		if(ma==null)
		{
			ma=1;
		}
		else {
			ma=ma+1;	
		}
		return ma.toString();
	}
	
	public List<DSTAIKHOAN> layAllTK()
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM DSTAIKHOAN";
		Query query = session.createQuery(hql);
		List<DSTAIKHOAN> list = query.list();
		return list;
	}
	
	public boolean insertTK1(DSTAIKHOAN tk)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.save(tk);
			t.commit();
			return true;
		}catch(Exception e){
			t.rollback();
			return false;
		}
		finally {
			session.close();
		}
	}
	
	public boolean insertNV1(NHANVIEN nv)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		System.out.print("\n"+ nv.getManv().toString());
		try {
			session.save(nv);
			t.commit();
			return true;
		}catch(Exception e){
			System.out.print("\n"+ e);
			t.rollback();
			return false;
		}
	}
	
	public List<NHANVIEN> layAllNV()
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM NHANVIEN";
		Query query = session.createQuery(hql);
		List<NHANVIEN> list = query.list();
		return list;
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
	
	public void updateTK1(DSTAIKHOAN tk)
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
	
	public boolean updateNV1(NHANVIEN nv)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		
		try {
			session.update(nv);
			t.commit();
			return true;
		}catch(Exception e){
			t.rollback();
			return false;
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
