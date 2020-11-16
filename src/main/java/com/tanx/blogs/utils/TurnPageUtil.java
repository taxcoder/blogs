package com.tanx.blogs.utils;


import java.util.List;

@SuppressWarnings("all")
public class TurnPageUtil<T> {
    private int currentPage;
    private int allPage;
    private int showPage;
    private int pageNumber;
    private List<T> archivesData;

    public TurnPageUtil() {}

    public TurnPageUtil(int allPage, int showPage) {
        this.allPage = allPage;
        this.showPage = showPage;
    }

    public TurnPageUtil(int currentPage, int allPage, int showPage) {
        this.currentPage = currentPage;
        this.allPage = allPage;
        this.showPage = showPage;
    }

    public TurnPageUtil(int currentPage, int allPage, int showPage, List<T> archivesData) {
        this.currentPage = currentPage;
        this.allPage = allPage;
        this.showPage = showPage;
        this.archivesData = archivesData;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setAllPage(int allPage) {
        this.allPage = allPage;
    }

    public void setShowPage(int showPage) {
        this.showPage = showPage;
    }

    public void setArchivesData(List<T> archivesData) {
        this.archivesData = archivesData;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getAllPage() {
        return this.allPage;
    }

    public int getShowPage() {
        return this.showPage;
    }


    public int getPageNumber() {
        if (this.allPage != 0 && this.showPage != 0 && this.allPage > this.showPage){
            return (((double) this.allPage / this.showPage) - (this.allPage / this.showPage) != 0.0D) ? (this.allPage / this.showPage + 1) : (this.allPage / this.showPage);
        }
        if (this.allPage != 0 && this.showPage != 0) {
            return 1;
        }
        return 1;
    }

    public List<T> getArchivesData() {
        return this.archivesData;
    }


    @Override
    public String toString() {
        return "TurnPageUtil{currentPage=" + this.currentPage + ", allPage=" + this.allPage + ", showPage=" + this.showPage + ", pageNumber=" + this.pageNumber + ", archivesData=" + this.archivesData + '}';
    }
}