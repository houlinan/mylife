package cn.houlinan.mylife.utils.org.n3r.idworker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {

	public static void main(String[] args) {

		for (int i = 0 ; i < 1000 ; i ++) {
			log.info(Sid.next());
		}
	}

}
