package com.xfxg99.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xfxg99.base.dao.SysParameterMapper;
import com.xfxg99.base.model.SysParameter;
import com.xfxg99.core.ListResult;

@Service("sysParameterService")
public class SysParameterService {

	@Resource(name = "sysParameterMapper")
	private SysParameterMapper sysParameterMapper;

	public ListResult<SysParameter> loadSysParamList() {
		// TODO Auto-generated method stub
		int count = sysParameterMapper.getParamCount();
		List<SysParameter> ls = sysParameterMapper.loadSysParamList();
		for (SysParameter p : ls) {
			if (p.getGroupname() != null) {
				p.setGroup(p.getGroupname());
			}
		}
		ListResult<SysParameter> result = new ListResult<SysParameter>(count,
				ls);

		return result;
	}

	public void insert(SysParameter paramters) {
		// TODO Auto-generated method stub
		sysParameterMapper.insert(paramters);
	}

	public void updateByPrimaryKey(SysParameter p) {
		// TODO Auto-generated method stub
		sysParameterMapper.updateByPrimaryKey(p);
	}

}
