package world;

import framework.Constants;

public enum EntityName { // 实体名称枚举类，用于判断游戏实体名称
	
	REDBABY(Constants.REDBABY_NAME), // 大娃
	ORANGEBABY(Constants.ORANGEBABY_NAME), // 二娃
	YELLOWBABY(Constants.YELLOWBABY_NAME), // 三娃
	GREENBABY(Constants.GREENBABY_NAME), // 四娃
	BLUEBABY(Constants.BLUEBABY_NAME), // 五娃
	INDIGOBABY(Constants.INDIGOBABY_NAME), // 六娃
	PURPLEBABY(Constants.PURPLEBABY_NAME), // 七娃
	GRANDFATHER(Constants.GRANDFATHER_NAME), // 爷爷
	SNAKE(Constants.SNAKE_NAME), // 蛇精
	SCORPION(Constants.SCORPION_NAME), // 蝎子精
	CHILOPOD(Constants.CHILOPOD_NAME), // 蜈蚣精
	CROCODILE(Constants.CROCODILE_NAME) // 鳄鱼精
	;
	
	private String name;
	
	private EntityName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static EntityName find(String str) { // 根据名称原始值查找名称枚举量
		for(EntityName n:EntityName.values()) {
			if (n.getName().equals(str)) {
				return n;
			}
		}
		return null;
	}
	
}
