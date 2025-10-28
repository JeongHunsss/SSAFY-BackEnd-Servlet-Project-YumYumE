package com.ssafy.yumyume.util.helper;

public interface ServiceHelper {
	default int getOffset(int pageNum, int limit) {
        return (pageNum - 1) * limit;
    }
}
