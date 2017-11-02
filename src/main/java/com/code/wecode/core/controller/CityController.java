package com.code.wecode.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.code.wecode.core.mapper.CityMapper;
import com.code.wecode.core.model.City;

@Controller
public class CityController {
	@Autowired
	private CityMapper cityMapper;

	@RequestMapping(value = "/api/city/{id}", method = RequestMethod.GET)
	@ResponseBody
	public City findOneCity(@PathVariable("id") int id) {
		return cityMapper.selectByPrimaryKey(id);
	}

	@RequestMapping(value = "/api/city", method = RequestMethod.GET)
	@ResponseBody
	public List<City> findAllCity() {
		return cityMapper.findAllCity();
	}

	@RequestMapping(value = "/api/city", method = RequestMethod.POST)
	@ResponseBody
	public void createCity(@RequestBody City city) {
		cityMapper.insert(city);
	}

	@RequestMapping(value = "/api/city", method = RequestMethod.PUT)
	@ResponseBody
	public void modifyCity(@RequestBody City city) {
		cityMapper.updateByPrimaryKeySelective(city);
	}

	@RequestMapping(value = "/api/city/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void modifyCity(@PathVariable("id") int id) {
		cityMapper.deleteByPrimaryKey(id);
	}
}
