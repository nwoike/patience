package com.patience.klondike.solver

import static com.google.common.collect.Lists.partition
import com.google.common.collect.Lists

class CycleDetector {

	boolean detect(List list, int repeatCount=3) {
		def partitions = []

		if (list.isEmpty()) return false

		(1..list.size() / 2).each { partitionSize ->
			if (partitionSize == 1) {
				partitions.add(partition(list, partitionSize))
				
			} else {
				List copy = new ArrayList(list)

				for (Iterator iterator = copy.iterator(); iterator.hasNext();) {
					iterator.next()

					if (copy.size() % partitionSize == 0) {
						partitions.add(partition(new ArrayList(copy), partitionSize))
					}

					iterator.remove()
				}
			}
		}

		!!partitions.find { List partition ->
			if (partition.size() - repeatCount < 0) {
				return false
			}

			Set tail = partition.subList(partition.size() - repeatCount, partition.size())
			tail.size() == 1
		}
	}
}