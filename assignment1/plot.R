setwd("xml/results")
tests = c("100_", "^400_", "850_", "1700_", "3400_", "6785_")

#read files
for(test in tests) {
	files = list.files(pattern=test)

	for(file in files) {
		if (!exists("testR")) {
			testR <- read.table(file, header=FALSE)
		}
		else {
			testR <- rbind(testR, read.table(file, header=FALSE))
			
	matplot(rownames(testR), pch = 1:4, type = "o", testR)
	rm(testR)
		}
	}
}
