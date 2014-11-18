package com.patience.klondike.solver

import java.util.List;

import spock.lang.Specification;
import spock.lang.Unroll;
import static com.patience.klondike.solver.CycleDetector.detect;

class CycleDetectorSpec extends Specification {

	def detector = new CycleDetector()
	
	def "can detect cycles"() {
		expect:		
		!detector.detect(["a"])
		detector.detect(["b", "a", "a"], 2)
		!detector.detect(["a", "b", "c"]) 
		!detector.detect(["a", "b", "c", "a", "b"])
		detector.detect(["a", "a", "b", "a", "b"], 2)
		detector.detect(["a", "a", "b", "c", "d", "a", "b", "c", "d"], 2)
		detector.detect(["a", "a", "b", "c", "d", "e", "f", "g", "h", "a", "b", "c", "d", "e", "f", "g", "h", "a", "b", "c", "d", "e", "f", "g", "h"], 3)
		detector.detect(["a", "b", "a", "b", "a", "b"], 2)	
		!detector.detect(["d", "f", "a", "b", "a", "b"], 3)
		detector.detect(["d", "f", "a", "b", "a", "b", "a", "b"], 3)	
		detector.detect(["a","b","c", "a", "b", "c"], 2)	
		detector.detect(["a", "b", "c", "a", "b", "c", "a", "b", "c"]) 
	}
}