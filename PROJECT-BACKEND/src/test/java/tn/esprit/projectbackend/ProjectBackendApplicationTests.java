package tn.esprit.projectbackend;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;


import tn.esprit.projectbackend.Entity.Portfolio;
import tn.esprit.projectbackend.Entity.Pridect;
import tn.esprit.projectbackend.Service.IPortfolioService;
import tn.esprit.projectbackend.Service.PortfolioServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;


@SpringBootTest
@Slf4j
class ProjectBackendApplicationTests {
	private PortfolioServiceImp yourServiceClass ;
	@Autowired
	IPortfolioService portfolioService;

	@Test
	public void testFetchDataFromApi() {
		List<Portfolio> result = portfolioService.fetchDataFromApi();
		Assertions.assertNotNull(result);

	}
//	@Test
//	public void testFetchDataFromApi1(){
//		Float result =portfolioService.predictionForVolume(Pridect p);
//		log.info("the result is "+ result);
//
//
//	}
}
