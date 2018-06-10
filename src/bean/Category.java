package bean;

public class Category {

	
	private String id;
	
	private String name;
	
	private String ownerId;
	
	private String type;
	
	public Category( String name) {
		this.name = name;
	}

	public Category(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Category(String id, String name, String ownerId, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.ownerId = ownerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

}
