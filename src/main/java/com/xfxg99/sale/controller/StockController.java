package com.xfxg99.sale.controller;

/*import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;*/
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
  

/*import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;*/
import net.sf.json.JSONObject; 

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.Organization;
import com.xfxg99.base.model.User;
import com.xfxg99.base.service.OrganizationService;
import com.xfxg99.base.viewmodel.UserVM;
import com.xfxg99.core.GeneralUtil;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result;
import com.xfxg99.sale.service.BillSerialNoService;
import com.xfxg99.sale.service.StockService;
import com.xfxg99.sale.viewmodel.InventoryVM;
import com.xfxg99.sale.viewmodel.StockBillVM;
import com.xfxg99.sale.viewmodel.StockGoodsVM;

@Scope("prototype")
@Controller
@RequestMapping("/stock")
public class StockController {

	@Resource(name = "stockService")
	protected StockService stockService;

	@Resource(name = "billSerialNoService")
	protected BillSerialNoService billSerialNoService;

	@Resource(name = "organizationService")
	protected OrganizationService orgService;

	/**
	 * 获取数据列表
	 * 
	 * @param query
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loadStockList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadStockList(
			@RequestParam(value = "stockQuery", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) {
		ListResult<StockBillVM> result = new ListResult<StockBillVM>();

		UserVM user = (UserVM) request.getSession().getAttribute("user");

		if (user == null) {
			result.setIsSuccess(false);
			result.setMsg("请重新登录");
			return result.toJson();
		}

		JSONObject joQuery = JSONObject.fromObject(query);
		Map<String, Object> map = new HashMap<String, Object>();

		int billType = joQuery.getInt("billType");
		int stockInOrgId = joQuery.getInt("stockInOrgId");
		int stockOutOrgId = joQuery.getInt("stockOutOrgId");
		String serialNo = joQuery.getString("serialNo").trim();
		serialNo = "".equals(serialNo) ? null : serialNo;
		String beginTime = joQuery.getString("beginTime");
		String endTime = joQuery.getString("endTime") + " 23:59:59";
		int confirmState = joQuery.getInt("confirmState");
		String goodsSerial = joQuery.getString("goodsSerial").trim();
		goodsSerial = "".equals(goodsSerial) ? null : goodsSerial;

		// page = page == 0 ? 1 : page;

		map.put("billType", billType);
		map.put("stockInOrgId", stockInOrgId);
		map.put("stockOutOrgId", stockOutOrgId);
		map.put("serialNo", serialNo);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("confirmState", confirmState);
		map.put("goodsSerial", goodsSerial);
		if (user.getIsAllDataPermission()) {
			Organization org = orgService.getOrganization(user.getOrgId());
			map.put("userOrgPath", org.getPath());
		}
		// map.put("pageStart", (page - 1) * rows);
		// map.put("pageSize", rows);

		result = stockService.loadVMListWithPage(map);

		return result.toJson();
	}

	/**
	 * 读取一张单据 如果id=0，表示新建一个单据返
	 * 
	 * @return
	 */
	@RequestMapping(value = "loadBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadBill(
			@RequestParam(value = "billType", required = false) Integer billType,
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {

		Result<StockBillVM> result = new Result<StockBillVM>();
		UserVM user = (UserVM) request.getSession().getAttribute("user");

		if (user == null) {
			result = new Result<StockBillVM>(null, false, true, false, "请从新登录!");
			return result.toJson();
		}

		StockBillVM bill = null;

		if (id == 0) {// 新建一个单据
			bill = this.newStockBill(billType, user);
		} else {// 从数据库读取一个单据
			bill = stockService.loadVMById(id);
		}

		result.setData(bill);

		return result.toJson();
	}

	@RequestMapping(value = "confirmBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String confirmBill(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {

		UserVM user = (UserVM) request.getSession().getAttribute("user");
		Result<StockBillVM> result = null;

		if (user == null) {
			result = new Result<StockBillVM>(null, false, true, false, "请从新登录");
			return result.toJson();
		}

		try {

			StockBillVM bill = stockService.loadVMById(id);

			String msg = "";
			boolean isSuccess = true;

			switch (bill.getBillType()) {
			case 10:
				if (bill.getStockInOrgId() != user.getOrgId()) {
					msg = "确认人必须属于入库部门!";
					isSuccess = false;
				}
				break;
			case 11:
				if (bill.getStockOutOrgId() != user.getOrgId()) {
					msg = "确认人必须属于出库部门!";
					isSuccess = false;
				}
				break;
			case 12:
				if (bill.getStockInOrgId() != user.getOrgId()) {
					msg = "确认人必须属于入库部门!";
					isSuccess = false;
				}
				break;
			}

			if (isSuccess) {
				stockService.confirmStockBill(id, user.getId());

				bill.setConfirmerId(user.getId());
				bill.setConfirmerName(user.getName());
				bill.setConfirmerOrgId(user.getOrgId());
				bill.setConfirmerOrgName(user.getOrgName());
				bill.setConfirmTime(Calendar.getInstance().getTime());

				result = new Result<StockBillVM>(bill, true, false, false, null);
			} else {
				result = new Result<StockBillVM>(bill, false, false, false, msg);
			}

		} catch (Exception ex) {
			result = new Result<StockBillVM>(null, false, false, true,
					ex.getMessage());
		}

		return result.toJson();
	}

	@RequestMapping(value = "saveStockBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveStockBill(
			@RequestParam(value = "bill", required = false) String billJson,
			HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");

		Result<StockBillVM> result = null;

		if (user == null) {
			result = new Result<StockBillVM>(null, false, true, false, "请从新登录");
			return result.toJson();
		}

		try {
			JSONObject jObj = JSONObject.fromObject(billJson);

			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();

			classMap.put("stockGoods", StockGoodsVM.class);

			StockBillVM bill = (StockBillVM) JSONObject.toBean(jObj,
					StockBillVM.class, classMap);

			stockService.saveStockBill(bill);

			result = new Result<StockBillVM>(bill);

		} catch (Exception ex) {
			result = new Result<StockBillVM>(null, false, true, true,
					ex.getMessage());
		}
		return result.toJson();

	}

	@RequestMapping(value = "delStockBill.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String delStockBill(
			@RequestParam(value = "id", required = false) Integer id,
			HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		Result<StockBillVM> result = null;

		if (user == null) {
			result = new Result<StockBillVM>(null, false, true, false, "请从新登录");
			return result.toJson();
		}

		int confirmerId = stockService.getBillConfirmerId(id);

		if (confirmerId > 0) {
			result = new Result<StockBillVM>(null, false, false, false,
					"单据已经审核，不能删除!");
		} else {
			stockService.deleteBillById(id);
			result = new Result<StockBillVM>(null, true, false, false, null);
		}

		return result.toJson();
	}

	@RequestMapping(value = "loadInventoryList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String loadInventoryList(
			@RequestParam(value = "inventoryQuery", required = false) String query,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ListResult<InventoryVM> result = null;

		if (user == null) {
			result = new ListResult<InventoryVM>(0, null, false);
			result.setIsSessionExpired(true);
			result.setMsg("请重新登录");
			return result.toJson();
		}

		JSONObject joQuery = JSONObject.fromObject(query);

		int orgId = joQuery.getInt("orgId");
		String goodsName = joQuery.getString("goodsName");
		goodsName = "".equals(goodsName) ? null : goodsName;

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("goodsName", goodsName);
		page = page == 0 ? 1 : page;

		map.put("pageStart", (page - 1) * rows);
		map.put("pageSize", rows);
		map.put("orgId", orgId);
		if (!user.getIsAllDataPermission()) {
			Organization org = orgService.getOrganization(orgId);
			map.put("userOrgPath", org.getPath());
		}

		// Object x=map.get("goodsName");

		int total = stockService.loadInventoryCount(map);

		List<InventoryVM> ls = stockService.loadInventoryList(map);
		List<InventoryVM> list = new ArrayList<InventoryVM>();
		for (InventoryVM iv : ls) {
			if (iv.getGoodsName() != null && iv.getGoodsName().length() > 0) {
				list.add(iv);
			}
		}
		result = new ListResult<InventoryVM>(total, list, true);

		return result.toJson();
	}

	/**
	 * 创建一个单据
	 * 
	 * @param billType
	 * @param u
	 * @return
	 */
	private StockBillVM newStockBill(Integer billType, UserVM u) {
		StockBillVM bill = new StockBillVM();

		Date ct = Calendar.getInstance().getTime();

		bill.setBillType(billType);
		bill.setBillTime(ct);

		Map<String, Object> billNoMap = GeneralUtil.getSerialNoPars(billType);
		String billNo = billSerialNoService.getNextBillSerialNo(billNoMap);

		bill.setSerialNo(billNo);
		bill.setId(0);
		bill.setPreparerOrgId(u.getOrgId());
		bill.setPreparerOrgName(u.getOrgName());
		bill.setPreparerId(u.getId());
		bill.setPrepareTime(ct);
		bill.setPreparerName(u.getName());
		bill.setState(0);

		return bill;
	}

/*	@RequestMapping(value = "exportexcel.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody String exportexcel(
//			@RequestParam(value = "columns", required = false) String columns,
//			@RequestParam(value = "rows", required = false) String rows,
			@RequestParam(value = "inventoryQuery", required = false) String query,
			HttpServletRequest request) {
		try{
			JSONObject joQuery = JSONObject.fromObject(query);
			int orgId = joQuery.getInt("orgId");
			String goodsName = joQuery.getString("goodsName");
			goodsName = "".equals(goodsName) ? null : goodsName;
	
			Map<String, Object> map = new HashMap<String, Object>();
			User user = (User) request.getSession().getAttribute("user");
	
			if (user == null) {
				return "{\"isSuccess\":false,\"path\":\"\",\"Message\":\"导出失败，请联系管理员\"}";
			}
			map.put("goodsName", goodsName);
			map.put("orgId", orgId);
			if (!user.getIsAllDataPermission()) {
				Organization org = orgService.getOrganization(orgId);
				map.put("userOrgPath", org.getPath());
			} 
	
			List<InventoryVM> ls = stockService.loadInventoryList(map);
			List<InventoryVM> list = new ArrayList<InventoryVM>();
			for (InventoryVM iv : ls) {
				if (iv.getGoodsName() != null && iv.getGoodsName().length() > 0) {
					list.add(iv);
				}
			}
			String serverPath = getClass().getResource("/").getFile().toString(); 
			serverPath = serverPath.substring(0, (serverPath.length() - 16));
			String path = createExcelFile(list,serverPath);
			if("".equals(path)){
				return "{\"isSuccess\":false,\"path\":\"\",\"Message\":\"创建文家出错，请联系管理员\"}";
			}else{
				return "{\"isSuccess\":true,\"path\":\""+path+"\",\"Message\":\"\"}";
			}
		}catch(Exception ex){
			return "{\"isSuccess\":false,\"path\":\"\",\"Message\":\"导出失败，请联系管理员\"}";
		}
	}

	private String createExcelFile(List<InventoryVM> list,String serverPath) throws WriteException, IOException {
		// TODO Auto-generated method stub
		String filePath = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		java.util.Date date = new java.util.Date();
		String str = sdf.format(date);
		filePath = "excelModel/" + str + "_StockData.xls";
		String realPath = serverPath + filePath;
		 WritableWorkbook wwb = Workbook.createWorkbook(new File(realPath));  
		 WritableSheet ws = wwb.createSheet("商品库存数据汇总统计", 0);  
         // 设置表格的列宽度  
         ws.setColumnView(0, 50);// 第一列宽14 
         ws.setColumnView(1, 50);// 第一列宽14  
         ws.setColumnView(2, 50);// 第一列宽14  
         ws.setColumnView(3, 50);// 第一列宽14  
         ws.setColumnView(4, 50);// 第一列宽14  
         ws.setColumnView(5, 50);// 第一列宽14  
         ws.setColumnView(6, 50);// 第一列宽14   
         ws.setColumnView(7, 50);// 第一列宽14   
         ws.setColumnView(8, 50);// 第一列宽14   
         // **************往工作表中添加数据*****************  
         // 定义字体格式：字体为：微软雅黑，24号子，加粗  
         WritableFont titleFont = new WritableFont(WritableFont.createFont("微软雅黑"), 24, WritableFont.NO_BOLD);  
         WritableFont contentFont = new WritableFont(WritableFont.createFont("楷体 _GB2312"), 12, WritableFont.NO_BOLD);  
         WritableCellFormat titleFormat = new WritableCellFormat(titleFont);  
         WritableCellFormat contentFormat = new WritableCellFormat(contentFont);  
         //WritableCellFormat contentFormat2 = new WritableCellFormat(contentFont);  

         contentFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);  
         // 设置格式居中对齐  
         titleFormat.setAlignment(jxl.format.Alignment.CENTRE);  
         contentFormat.setAlignment(jxl.format.Alignment.CENTRE);  
         
         // ***************将定义好的单元格添加到工作表中*****************  
         ws.mergeCells(0, 0, 16, 0);// 合并单元格A-G共7列  
         ws.addCell(new Label(0, 0, "表1-1 商品库存数据汇总统计表[1]（商品库存、调拨、销售情况）", titleFormat));
         ws.addCell(new Label(1, 1, "序号", contentFormat)); 
         ws.addCell(new Label(2, 1, "部门名称", contentFormat));
         ws.addCell(new Label(3, 1, "商品名称", contentFormat));
         ws.addCell(new Label(4, 1, "现有数量", contentFormat));
         ws.addCell(new Label(5, 1, "入库数量", contentFormat));
         ws.addCell(new Label(6, 1, "出库数量", contentFormat));
         ws.addCell(new Label(7, 1, "调拨数量", contentFormat)); 
         ws.addCell(new Label(8, 1, "总销售量", contentFormat)); 
         for (int j = 0; j <list.size(); j++) {  
        	 InventoryVM inventory =list.get(j);   
             ws.addCell(new Label(1, j+3, j+1+"", contentFormat));  
             ws.addCell(new Label(2, j+3, "" + inventory.getOrgName(), contentFormat));  
             ws.addCell(new Label(3, j+3, "" + inventory.getGoodsName(), contentFormat));  
             ws.addCell(new Label(4, j+3, "" + inventory.getCurrentNumber(), contentFormat));  
             ws.addCell(new Label(5, j+3, ""  +inventory.getGoodsNumber(), contentFormat));  
             ws.addCell(new Label(6, j+3, "" + inventory.getStkOutNumber(), contentFormat));  
             ws.addCell(new Label(7, j+3, ""+inventory.getStkDbNumber(), contentFormat));  
             ws.addCell(new Label(8, j+3, "" +inventory.getSaleNumber(), contentFormat));   
         }
		try {
			 wwb.write();  
	         wwb.close();  
		} catch (Exception e) {
			return "";
		}
		return filePath;
	}*/
}
