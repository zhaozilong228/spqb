package spqbs.top.music.common.util;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<T> content;
	private Integer totalElements;
	
	public Page(List<T> content, Integer count) {
		super();
		this.content = content;
		this.totalElements = count;
	}
	
	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	@Override
	public String toString() {
		return "Page [content=" + content + ", totalElements=" + totalElements + "]";
	}
	
}
