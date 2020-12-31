package world;

public class DefendEntity extends Entity { // 防御实体，用于在角色实体防御产生的临时实体

	public DefendEntity(String name) {
		super(name);
		setDefendable(true); // 具有防御性
	}

}
