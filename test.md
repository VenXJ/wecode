# 业务描述
    银行账户分为对公账户和对私账户，代付打款时，因通道方对对公和对私收费不同，为保证平台和通道方费率的一致性，
    需要各系系统增加代付对公账户的支持。实现代付对公和对私费率的区分。

## 一、内管配置修改

### 商户费率配置修改
    商户代付费率，由原来的只配置混合卡费率，改为配置借记卡，贷记卡和混合卡费率，其中借记卡和贷记卡表示对私账户，混合卡表示对公账户

### 渠道费率配置修改
    渠道代付费率，由原来的只配置混合卡费率，改为配置借记卡，贷记卡和混合卡费率，其中借记卡和贷记卡表示对私账户，混合卡表示对公账户<br/>
    渠道银行，新增银行代码为“00000000”，银行名称为“全部银行”，用以路由通道。
    
## 二、内部交易代码修改
### 1. 路由通道方法修改
  - 类名：ChannelService4dImpl.java
  - package： com.ielpm.pay.core.service.impl</br>
    
    *修改前代码：123行开始*</br>
    ``` java
    if(payType.equals("30") || payType.equals("31")){//30单笔代付，31批量代付
    			if(!isDFOpen(ReqMap,payType)){
					logger.info("商户无此交易权限");
					results.setCode("0003");
					results.setMsg("无此交易权限");
					throw new Exception(results.toString());
				}
	  }
    ```
    *修改后代码：123行开始*</br>
    ``` java
    if(payType.equals("30") || payType.equals("31")){//30单笔代付，31批量代付
				if(!isDFOpen(ReqMap,payType)){
					logger.info("商户无此交易权限");
					results.setCode("0003");
					results.setMsg("无此交易权限");
					throw new Exception(results.toString());
				}
				if("*".equals(bankId)){//若bankId为*，且为代付交易，则只验证商户代付权限，不做通道筛选
					logger.info("商户已开通代付权限");
					results.setCode("0000");
					results.setMsg("权限验证成功");
					throw new Exception(results.toString());
				}
			}
      ```

### 2. 批量代付手续费查询方法修改
    因批量代付是从商户系统发起，且商户审核后会扣减商户余额，故批量代付文件上传时需要计算出每笔订单的手续费，
    原代码默认查询混合卡的代付费率。修改后需要查询该商户下配置的所有卡类型费率。
  - 类名：DFpayAction.java
  - package： com.eilpm.core.action</br>
    
    *修改前代码：96行开始*</br>
    ``` java
     List<MerFeeRate> list = new ArrayList<MerFeeRate>();
		if (result.getCode().equals(Status.SUCCESS)) {
			try {
				// 反序列化数据
				Map<String, String> tranDataMap = (Map<String, String>) result.getAttachment();
				String merchantNo = tranDataMap.get("merchantNo");
				MerFeeRate merFeeRate = null;
				MerFeeRate merFeeRate_T0 = null;
				merFeeRate = merFeeService.getUseFeeRateByTranType(merchantNo, "***", 40, "04", "3");//代付
				merFeeRate_T0 = merFeeService.getUseFeeRateByTranType(merchantNo, "***", 41, "04", "3");//T+0代付
				if (merFeeRate == null) {
					logger.info("没有找到可用的手续费merchantNo:" + merchantNo);
				}
				list.add(merFeeRate);
				list.add(merFeeRate_T0);
			}catch(Exception e){
				logger.error(e);
			}
		}
    ```
    *修改后代码：97行开始*</br>
    ``` java
    List<Map<String, MerFeeRate>> list = new ArrayList<Map<String, MerFeeRate>>();
		Map<String, MerFeeRate> feeT1 = new HashMap<String, MerFeeRate>();
		Map<String, MerFeeRate> feeT0 = new HashMap<String, MerFeeRate>();
		if (result.getCode().equals(Status.SUCCESS)) {
			try {
				// 反序列化数据
				Map<String, String> tranDataMap = (Map<String, String>) result.getAttachment();
				String merchantNo = tranDataMap.get("merchantNo");
				String cardType = null;
				MerFeeRate merFeeRate = null;
				MerFeeRate merFeeRate_T0 = null;
				//分别获取商户卡类型为01,02,04的代付费率
				cardType = "01";
				merFeeRate = merFeeService.getUseFeeRateByTranType(merchantNo, "***", 40, cardType, "3");//代付
				merFeeRate_T0 = merFeeService.getUseFeeRateByTranType(merchantNo, "***", 41, cardType, "3");//T+0代付
				if(null !=merFeeRate && cardType.equals(merFeeRate.getCARDTYPE())){
					feeT1.put(cardType, merFeeRate);
					feeT0.put(cardType, merFeeRate_T0);
				}else{
					feeT1.put(cardType, null);
					feeT0.put(cardType, null);
				}
				cardType = "02";
				merFeeRate = merFeeService.getUseFeeRateByTranType(merchantNo, "***", 40, cardType, "3");//代付
				merFeeRate_T0 = merFeeService.getUseFeeRateByTranType(merchantNo, "***", 41, cardType, "3");//T+0代付
				if(null !=merFeeRate && cardType.equals(merFeeRate.getCARDTYPE())){
					feeT1.put(cardType, merFeeRate);
					feeT0.put(cardType, merFeeRate_T0);
				}else{
					feeT1.put(cardType, null);
					feeT0.put(cardType, null);
				}
				cardType = "04";
				merFeeRate = merFeeService.getUseFeeRateByTranType(merchantNo, "***", 40, cardType, "3");//代付
				merFeeRate_T0 = merFeeService.getUseFeeRateByTranType(merchantNo, "***", 41, cardType, "3");//T+0代付
				feeT1.put(cardType, merFeeRate);
				feeT0.put(cardType, merFeeRate_T0);
				if (merFeeRate == null) {
					logger.info("没有找到可用的手续费merchantNo:" + merchantNo);
				}
				list.add(feeT1);
				list.add(feeT0);
			}catch(Exception e){
				logger.error(e);
			}
		}
    ```

     
