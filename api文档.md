# SpringCloud微服务系统API


**简介**:SpringCloud微服务系统API


**HOST**:http://localhost:8080


**联系人**:hyx


**Version**:1.0


**接口路径**:/v3/api-docs/zzq


[TOC]



# 产品管理


## 添加产品信息


**接口地址**:`/products`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>添加新的产品信息到数据库，包括名称、描述和初始库存量。</p>



**请求示例**:


```javascript
{
  "productId": 0,
  "name": "",
  "description": "",
  "price": 0,
  "productionDate": "",
  "id": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|product|Product|body|true|Product|Product|
|&emsp;&emsp;productId|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;description|||false|string||
|&emsp;&emsp;price|||false|number||
|&emsp;&emsp;productionDate|||false|string(date)||
|&emsp;&emsp;id|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|201|Created|EntityModelProduct|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|description||string||
|price||number||
|productionDate||string(date)|string(date)|
|id||integer(int32)|integer(int32)|
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "",
	"description": "",
	"price": 0,
	"productionDate": "",
	"id": 0,
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## 获取产品信息


**接口地址**:`/products/{productId}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>根据产品ID获取产品详细信息。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|productId||path|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 删除产品信息


**接口地址**:`/products/{productId}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>从数据库中删除指定的产品信息。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|productId||path|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 更新产品信息


**接口地址**:`/products/{productId}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>更新指定产品的信息，包括名称、描述等。</p>



**请求示例**:


```javascript
{
  "productId": 0,
  "name": "",
  "description": "",
  "price": 0,
  "productionDate": "",
  "id": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|productId||path|true|integer(int32)||
|product|Product|body|true|Product|Product|
|&emsp;&emsp;productId|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;description|||false|string||
|&emsp;&emsp;price|||false|number||
|&emsp;&emsp;productionDate|||false|string(date)||
|&emsp;&emsp;id|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


# 出库任务管理


## 查询出库任务


**接口地址**:`/outbound-tasks`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>按状态或日期范围查询出库任务。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|status||query|false|string||
|startDate||query|false|string||
|endDate||query|false|string||
|page||query|false|integer(int32)||
|size||query|false|integer(int32)||
|sort1||query|false|string||
|sort2||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 创建出库任务


**接口地址**:`/outbound-tasks`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>为订单创建出库任务。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|orderId||query|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 更新出库任务状态


**接口地址**:`/outbound-tasks/{taskId}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>更新出库任务的状态。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|taskId||path|true|integer(int32)||
|status||query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


# 订单管理


## 查询订单


**接口地址**:`/orders`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>根据用户或状态查询订单。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|page|Zero-based page index (0..N)|query|false|integer||
|size|The size of the page to be returned|query|false|integer||
|sort|Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.|query|false|array|string|
|userId||query|false|integer(int32)||
|status||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|PagedModelEntityModelOrder|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|_embedded||PagedModelEntityModelOrder__embedded|PagedModelEntityModelOrder__embedded|
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||
|page||PageMetadata|PageMetadata|
|&emsp;&emsp;size||integer(int64)||
|&emsp;&emsp;totalElements||integer(int64)||
|&emsp;&emsp;totalPages||integer(int64)||
|&emsp;&emsp;number||integer(int64)||


**响应示例**:
```javascript
{
	"_embedded": {
		"orders": [
			{
				"status": "",
				"createdAt": "",
				"updatedAt": "",
				"id": 0,
				"_links": [
					{
						"href": "",
						"hreflang": "",
						"title": "",
						"type": "",
						"deprecation": "",
						"profile": "",
						"name": "",
						"templated": true
					}
				]
			}
		]
	},
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	],
	"page": {
		"size": 0,
		"totalElements": 0,
		"totalPages": 0,
		"number": 0
	}
}
```


## 创建订单


**接口地址**:`/orders`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>创建一个新订单，同时进行库存检查和用户验证。</p>



**请求示例**:


```javascript
{
  "orderId": 0,
  "customer": {
    "id": 0,
    "name": "15",
    "password": "15",
    "email": "2334579301@qq.com",
    "phone": "133",
    "address": "北京市海淀区北京交通大学"
  },
  "status": "",
  "createdAt": "",
  "updatedAt": "",
  "id": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|order|Order|body|true|Order|Order|
|&emsp;&emsp;orderId|||false|integer(int32)||
|&emsp;&emsp;customer|User||false|User|User|
|&emsp;&emsp;&emsp;&emsp;id|||false|integer||
|&emsp;&emsp;&emsp;&emsp;name|||false|string||
|&emsp;&emsp;&emsp;&emsp;password|||false|string||
|&emsp;&emsp;&emsp;&emsp;email|||false|string||
|&emsp;&emsp;&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;&emsp;&emsp;address|||false|string||
|&emsp;&emsp;status|||false|string||
|&emsp;&emsp;createdAt|||false|string(date-time)||
|&emsp;&emsp;updatedAt|||false|string(date-time)||
|&emsp;&emsp;id|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|201|Created|EntityModelOrder|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|status||string||
|createdAt||string(date-time)|string(date-time)|
|updatedAt||string(date-time)|string(date-time)|
|id||integer(int32)|integer(int32)|
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"status": "",
	"createdAt": "",
	"updatedAt": "",
	"id": 0,
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## 取消订单


**接口地址**:`/orders/{orderId}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>取消订单，并回滚库存。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|orderId||path|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 更新订单状态


**接口地址**:`/orders/{orderId}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>更新订单的状态。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|orderId||path|true|integer(int32)||
|status||query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


# 订单商品管理


## 添加商品到订单


**接口地址**:`/order-items`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>为特定订单添加商品。</p>



**请求示例**:


```javascript
{
  "orderItemId": 0,
  "order": {
    "orderId": 0,
    "customer": {
      "id": 0,
      "name": "15",
      "password": "15",
      "email": "2334579301@qq.com",
      "phone": "133",
      "address": "北京市海淀区北京交通大学"
    },
    "status": "",
    "createdAt": "",
    "updatedAt": "",
    "id": 0
  },
  "product": {
    "productId": 0,
    "name": "",
    "description": "",
    "price": 0,
    "productionDate": "",
    "id": 0
  },
  "quantity": 0
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|orderItem|OrderItem|body|true|OrderItem|OrderItem|
|&emsp;&emsp;orderItemId|||false|integer(int32)||
|&emsp;&emsp;order|||false|Order|Order|
|&emsp;&emsp;&emsp;&emsp;orderId|||false|integer||
|&emsp;&emsp;&emsp;&emsp;customer|User||false|User|User|
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;id|||false|integer||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;name|||false|string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;password|||false|string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;email|||false|string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;address|||false|string||
|&emsp;&emsp;&emsp;&emsp;status|||false|string||
|&emsp;&emsp;&emsp;&emsp;createdAt|||false|string||
|&emsp;&emsp;&emsp;&emsp;updatedAt|||false|string||
|&emsp;&emsp;&emsp;&emsp;id|||false|integer||
|&emsp;&emsp;product|||false|Product|Product|
|&emsp;&emsp;&emsp;&emsp;productId|||false|integer||
|&emsp;&emsp;&emsp;&emsp;name|||false|string||
|&emsp;&emsp;&emsp;&emsp;description|||false|string||
|&emsp;&emsp;&emsp;&emsp;price|||false|number||
|&emsp;&emsp;&emsp;&emsp;productionDate|||false|string||
|&emsp;&emsp;&emsp;&emsp;id|||false|integer||
|&emsp;&emsp;quantity|||false|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 删除订单商品


**接口地址**:`/order-items/{orderItemId}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>从订单中删除一个商品，并更新库存。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|orderItemId||path|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 更新订单商品


**接口地址**:`/order-items/{orderItemId}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>更新订单中某个商品的数量。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|orderItemId||path|true|integer(int32)||
|newQuantity||query|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


# 订单异常管理


## 查询异常


**接口地址**:`/order-exceptions`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>按订单ID或日期查询异常记录。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|orderId||query|false|integer(int32)||
|date||query|false|string(date)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 记录异常


**接口地址**:`/order-exceptions`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>记录订单处理中的异常。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|orderId||query|true|integer(int32)||
|description||query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 更新异常记录


**接口地址**:`/order-exceptions/{exceptionId}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>更新异常描述或状态。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|exceptionId||path|true|integer(int32)||
|newDescription||query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


# 获取工作人员


## getCollectionResource-worker-get_1


**接口地址**:`/WorkerRepository`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>get-worker</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|page|Zero-based page index (0..N)|query|false|integer||
|size|The size of the page to be returned|query|false|integer||
|sort|Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.|query|false|array|string|


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|PagedModelEntityModelWorker|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|_embedded||PagedModelEntityModelWorker__embedded|PagedModelEntityModelWorker__embedded|
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||
|page||PageMetadata|PageMetadata|
|&emsp;&emsp;size||integer(int64)||
|&emsp;&emsp;totalElements||integer(int64)||
|&emsp;&emsp;totalPages||integer(int64)||
|&emsp;&emsp;number||integer(int64)||


**响应示例**:
```javascript
{
	"_embedded": {
		"workers": [
			{
				"name": "",
				"password": "",
				"role": "",
				"_links": [
					{
						"href": "",
						"hreflang": "",
						"title": "",
						"type": "",
						"deprecation": "",
						"profile": "",
						"name": "",
						"templated": true
					}
				]
			}
		]
	},
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	],
	"page": {
		"size": 0,
		"totalElements": 0,
		"totalPages": 0,
		"number": 0
	}
}
```


## postCollectionResource-worker-post


**接口地址**:`/WorkerRepository`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>create-worker</p>



**请求示例**:


```javascript
{
  "id": 0,
  "name": "",
  "password": "",
  "role": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|workerRequestBody|WorkerRequestBody|body|true|WorkerRequestBody|WorkerRequestBody|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;role|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|201|Created|EntityModelWorker|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|role||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "",
	"password": "",
	"role": "",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## getItemResource-worker-get


**接口地址**:`/WorkerRepository/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>get-worker</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|EntityModelWorker|
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|role||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "",
	"password": "",
	"role": "",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## putItemResource-worker-put


**接口地址**:`/WorkerRepository/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>update-worker</p>



**请求示例**:


```javascript
{
  "id": 0,
  "name": "",
  "password": "",
  "role": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|string||
|workerRequestBody|WorkerRequestBody|body|true|WorkerRequestBody|WorkerRequestBody|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;role|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|EntityModelWorker|
|201|Created|EntityModelWorker|
|204|No Content||


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|role||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "",
	"password": "",
	"role": "",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


**响应状态码-201**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|role||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "",
	"password": "",
	"role": "",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## deleteItemResource-worker-delete


**接口地址**:`/WorkerRepository/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>delete-worker</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|204|No Content||
|404|Not Found||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## patchItemResource-worker-patch


**接口地址**:`/WorkerRepository/{id}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>patch-worker</p>



**请求示例**:


```javascript
{
  "id": 0,
  "name": "",
  "password": "",
  "role": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|string||
|workerRequestBody|WorkerRequestBody|body|true|WorkerRequestBody|WorkerRequestBody|
|&emsp;&emsp;id|||false|integer(int64)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;role|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|EntityModelWorker|
|204|No Content||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|role||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "",
	"password": "",
	"role": "",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## executeSearch-worker-get


**接口地址**:`/WorkerRepository/search/existsByName`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|username||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||
|404|Not Found||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## executeSearch-worker-get_1


**接口地址**:`/WorkerRepository/search/existsByNameAndPassword`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|username||query|false|string||
|password||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||
|404|Not Found||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## executeSearch-worker-get_2


**接口地址**:`/WorkerRepository/search/findByName`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|username||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|EntityModelWorker|
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|role||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "",
	"password": "",
	"role": "",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


# 库存管理


## 查询库存


**接口地址**:`/inventory`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>查询特定产品的当前库存量。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|productId||query|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 更新库存


**接口地址**:`/inventory/{productId}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>更新指定产品的库存数量。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|productId||path|true|integer(int32)||
|changeInQuantity||query|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 库存警告


**接口地址**:`/inventory/warnings`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>查询库存量低于安全水平的所有产品。</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


# 权限管理


## 登录


**接口地址**:`/auth/login`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>登录</p>



**请求示例**:


```javascript
{
  "username": "15",
  "password": "15"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|loginDomain|LoginDomain|body|true|LoginDomain|LoginDomain|
|&emsp;&emsp;username|username||true|string||
|&emsp;&emsp;password|password||true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 登出


**接口地址**:`/auth/logout`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>登出</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 登出


**接口地址**:`/auth/logout`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>登出</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 登出


**接口地址**:`/auth/logout`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>登出</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 登出


**接口地址**:`/auth/logout`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>登出</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 登出


**接口地址**:`/auth/logout`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>登出</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 登出


**接口地址**:`/auth/logout`


**请求方式**:`OPTIONS`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>登出</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 登出


**接口地址**:`/auth/logout`


**请求方式**:`HEAD`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>登出</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 注册


**接口地址**:`/auth/register`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>注册</p>



**请求示例**:


```javascript
{
  "username": "Test",
  "password1": "z1111",
  "password2": "z1111",
  "email": "test@example.com",
  "phone": "18810393672"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|registerDomain|RegisterDomain|body|true|RegisterDomain|RegisterDomain|
|&emsp;&emsp;username|||true|string||
|&emsp;&emsp;password1|||true|string||
|&emsp;&emsp;password2|||true|string||
|&emsp;&emsp;email|||false|string||
|&emsp;&emsp;phone|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


# 入库任务管理


## 创建入库任务


**接口地址**:`/inbound-tasks`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>创建一个新的入库任务。</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 删除入库任务


**接口地址**:`/inbound-tasks/{taskId}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>删除指定ID的入库任务。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|taskId||path|true|integer(int32)||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 更新入库任务状态


**接口地址**:`/inbound-tasks/{taskId}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>更新入库任务的状态。</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|taskId||path|true|integer(int32)||
|status||query|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


# 数据库User接口


## getCollectionResource-user-get_1


**接口地址**:`/UserRepository`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>get-user</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|page|Zero-based page index (0..N)|query|false|integer||
|size|The size of the page to be returned|query|false|integer||
|sort|Sorting criteria in the format: property,(asc|desc). Default sort order is ascending. Multiple sort criteria are supported.|query|false|array|string|


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|PagedModelEntityModelUser|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|_embedded||PagedModelEntityModelUser__embedded|PagedModelEntityModelUser__embedded|
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||
|page||PageMetadata|PageMetadata|
|&emsp;&emsp;size||integer(int64)||
|&emsp;&emsp;totalElements||integer(int64)||
|&emsp;&emsp;totalPages||integer(int64)||
|&emsp;&emsp;number||integer(int64)||


**响应示例**:
```javascript
{
	"_embedded": {
		"users": [
			{
				"name": "15",
				"password": "15",
				"email": "2334579301@qq.com",
				"phone": "133",
				"address": "北京市海淀区北京交通大学",
				"_links": [
					{
						"href": "",
						"hreflang": "",
						"title": "",
						"type": "",
						"deprecation": "",
						"profile": "",
						"name": "",
						"templated": true
					}
				]
			}
		]
	},
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	],
	"page": {
		"size": 0,
		"totalElements": 0,
		"totalPages": 0,
		"number": 0
	}
}
```


## postCollectionResource-user-post


**接口地址**:`/UserRepository`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>create-user</p>



**请求示例**:


```javascript
{
  "id": 0,
  "name": "15",
  "password": "15",
  "email": "2334579301@qq.com",
  "phone": "133",
  "address": "北京市海淀区北京交通大学"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|userRequestBody|User|body|true|UserRequestBody|UserRequestBody|
|&emsp;&emsp;id|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;email|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;address|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|201|Created|EntityModelUser|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|email||string||
|phone||string||
|address||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "15",
	"password": "15",
	"email": "2334579301@qq.com",
	"phone": "133",
	"address": "北京市海淀区北京交通大学",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## getItemResource-user-get


**接口地址**:`/UserRepository/{id}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>get-user</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|EntityModelUser|
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|email||string||
|phone||string||
|address||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "15",
	"password": "15",
	"email": "2334579301@qq.com",
	"phone": "133",
	"address": "北京市海淀区北京交通大学",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## putItemResource-user-put


**接口地址**:`/UserRepository/{id}`


**请求方式**:`PUT`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>update-user</p>



**请求示例**:


```javascript
{
  "id": 0,
  "name": "15",
  "password": "15",
  "email": "2334579301@qq.com",
  "phone": "133",
  "address": "北京市海淀区北京交通大学"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|string||
|userRequestBody|User|body|true|UserRequestBody|UserRequestBody|
|&emsp;&emsp;id|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;email|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;address|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|EntityModelUser|
|201|Created|EntityModelUser|
|204|No Content||


**响应状态码-200**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|email||string||
|phone||string||
|address||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "15",
	"password": "15",
	"email": "2334579301@qq.com",
	"phone": "133",
	"address": "北京市海淀区北京交通大学",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


**响应状态码-201**:


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|email||string||
|phone||string||
|address||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "15",
	"password": "15",
	"email": "2334579301@qq.com",
	"phone": "133",
	"address": "北京市海淀区北京交通大学",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## deleteItemResource-user-delete


**接口地址**:`/UserRepository/{id}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:<p>delete-user</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|204|No Content||
|404|Not Found||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## patchItemResource-user-patch


**接口地址**:`/UserRepository/{id}`


**请求方式**:`PATCH`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>patch-user</p>



**请求示例**:


```javascript
{
  "id": 0,
  "name": "15",
  "password": "15",
  "email": "2334579301@qq.com",
  "phone": "133",
  "address": "北京市海淀区北京交通大学"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|id||path|true|string||
|userRequestBody|User|body|true|UserRequestBody|UserRequestBody|
|&emsp;&emsp;id|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;email|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;address|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|EntityModelUser|
|204|No Content||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|email||string||
|phone||string||
|address||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "15",
	"password": "15",
	"email": "2334579301@qq.com",
	"phone": "133",
	"address": "北京市海淀区北京交通大学",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## 通过用户名删除用户


**接口地址**:`/UserRepository/search/deleteByName`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||
|404|Not Found||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## 是否存在名字


**接口地址**:`/UserRepository/search/existsByName`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||
|404|Not Found||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## 是否存在用户名和密码


**接口地址**:`/UserRepository/search/existsByNameAndPassword`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name||query|false|string||
|password||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||
|404|Not Found||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## 通过用户名查找


**接口地址**:`/UserRepository/search/findByName`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|EntityModelUser|
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|email||string||
|phone||string||
|address||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "15",
	"password": "15",
	"email": "2334579301@qq.com",
	"phone": "133",
	"address": "北京市海淀区北京交通大学",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


## 通过用户名和密码查找出一个用户


**接口地址**:`/UserRepository/search/findByNameAndPassword`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name||query|false|string||
|password||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|EntityModelUser|
|404|Not Found||


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|name||string||
|password||string||
|email||string||
|phone||string||
|address||string||
|_links||array|Link|
|&emsp;&emsp;href||string||
|&emsp;&emsp;hreflang||string||
|&emsp;&emsp;title||string||
|&emsp;&emsp;type||string||
|&emsp;&emsp;deprecation||string||
|&emsp;&emsp;profile||string||
|&emsp;&emsp;name||string||
|&emsp;&emsp;templated||boolean||


**响应示例**:
```javascript
{
	"name": "15",
	"password": "15",
	"email": "2334579301@qq.com",
	"phone": "133",
	"address": "北京市海淀区北京交通大学",
	"_links": [
		{
			"href": "",
			"hreflang": "",
			"title": "",
			"type": "",
			"deprecation": "",
			"profile": "",
			"name": "",
			"templated": true
		}
	]
}
```


# 用户管理


## 查询所有用户


**接口地址**:`/user`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>查询所有用户</p>



**请求参数**:


暂无


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 创建用户


**接口地址**:`/user`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>创建用户</p>



**请求示例**:


```javascript
{
  "id": 0,
  "name": "15",
  "password": "15",
  "email": "2334579301@qq.com",
  "phone": "133",
  "address": "北京市海淀区北京交通大学"
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|user|User|body|true|User|User|
|&emsp;&emsp;id|||false|integer(int32)||
|&emsp;&emsp;name|||false|string||
|&emsp;&emsp;password|||false|string||
|&emsp;&emsp;email|||false|string||
|&emsp;&emsp;phone|||false|string||
|&emsp;&emsp;address|||false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 查询单个用户


**接口地址**:`/user/{name}`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>查询单个用户</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name||path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```


## 删除用户


**接口地址**:`/user/{name}`


**请求方式**:`DELETE`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`application/hal+json`


**接口描述**:<p>删除用户</p>



**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|name||path|true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|Result|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|success||boolean||
|message||string||
|data||object||
|errors||array||
|devMessages||array||
|status||string||


**响应示例**:
```javascript
{
	"success": true,
	"message": "",
	"data": {},
	"errors": [],
	"devMessages": [],
	"status": ""
}
```