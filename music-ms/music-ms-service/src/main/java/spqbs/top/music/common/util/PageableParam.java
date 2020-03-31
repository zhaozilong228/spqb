package spqbs.top.music.common.util;

public class PageableParam {
	private Integer page = 1;
	private Integer size = 10;
	private Boolean closePage;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Boolean getClosePage() {
		return closePage;
	}
	public void setClosePage(Boolean closePage) {
		this.closePage = closePage;
	}
	@Override
	public String toString() {
		return "PageableParam [page=" + page + ", size=" + size + ", closePage=" + closePage + "]";
	}
	

}
