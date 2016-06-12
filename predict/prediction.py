import pandas as pd
from sklearn import svm,cross_validation

rowlist = []

# f = open("dataset.tsv",'r+')

with open('dataset1.tsv') as f:
    rowlist = [line.strip().split('\t') for line in f]

# print("number of columns in input file is: ", len(rowlist[0]))
# print("assuming the file has no header")
print("row count in input file is: ", len(rowlist))
# print(rowlist[:1])

# rowlist = rowlist[:500]
print("applying the problem only for: ", len(rowlist), " rows")

data = pd.DataFrame({
    "Country": [item[0] for item in rowlist],
    "Year": [item[1] for item in rowlist],
    "tp10": [item[3] for item in rowlist],
    "tp5": [item[4] for item in rowlist],
    "tp1": [item[5] for item in rowlist],
    "t": [item[6] for item in rowlist],
    "tf1": [item[7] for item in rowlist],
    "tf5": [item[8] for item in rowlist],
    "tf10": [item[9] for item in rowlist],
    "item": [item[2] for item in rowlist],
    "op10": [item[10] for item in rowlist],
    "op5": [item[11] for item in rowlist],
    "op1": [item[12] for item in rowlist],
    "o": [item[13] for item in rowlist],
    "of1": [item[14] for item in rowlist],
    "of5": [item[15] for item in rowlist],
    "of10": [item[16] for item in rowlist]
})


print("converting to pandas dataframes")
# print data
print("total rows in the dataframe: ", len(data))
print("cleaning up null value rows for the essential fields")
data = data[(data.Country != 'null') & (data.Year != 'null') & (data.tp10 != 'null')  & (data.tp10 != '0') & (data.tp5 != 'null')  & (data.tp5 != '0') & (data.tp1 != 'null')  & (data.tp1 != '0') & (data.t != 'null')  & (data.t != '0') & (data.item != 'null')  & (data.op10 != 'null') & (data.op10 != '0') & (data.op5 != 'null') & (data.op5 != '0') & (data.op1 != 'null') & (data.op1 != '0') & (data.o != 'null') & (data.o != '0')]
print("total rows in the dataframe: ",len(data))
print("making category columns for country and item")
data["Country"] = data["Country"].astype('category')
data["item"] = data["item"].astype('category')
# print(data[:3])
data["Country"] = data["Country"].cat.codes
data["item"] = data["item"].cat.codes
# print(data[:3])



# data1 = data[["Country","Year","tp10","tp5","tp1","t","tf1","tf5","tf10"]]
data1 = data[["Country","Year","tp10","tp5","tp1","t","tf1","tf5","tf10"]]
data2 = data[["item","op10","op5","op1","o","of1","of5","of10"]]

print "started"




data_train = data1[(data1.tf1 != 'null') & (data1.tf1 != '0')]
data_predict = data1[(data1.tf1 == 'null') | (data1.tf1 == '0')]
# print(len(data_train.index))
# print(len(data_predict.index))

# print(data_predict)

clf = svm.SVC(gamma=0.0001,C=100)
clf.fit( data_train[["Country","Year","tp10","tp5","tp1","t"]],data_train["tf1"])
data_predict["tf1"] = clf.predict(data_predict[["Country", "Year", "tp10","tp5","tp1","t"]])



# print(data_predict["tf10"][:3])
# print(data_predict)


data1 = pd.concat([data_train, data_predict])
# print(len(data1.index))
# print(data1[:3])




data_train = data1[(data1.tf5 != 'null') & (data1.tf5 != '0')]
data_predict = data1[(data1.tf5 == 'null') | (data1.tf5 == '0')]
# print(len(data_train.index))
# print(len(data_predict.index))

# print(data_predict)

clf = svm.SVC(gamma=0.0001,C=100)
clf.fit( data_train[["Country","Year","tp10","tp5","tp1","t","tf1"]],data_train["tf5"])
data_predict["tf5"] = clf.predict(data_predict[["Country", "Year", "tp10","tp5","tp1","t","tf1"]])



# print(data_predict["tf10"][:3])
# print(data_predict)


data1 = pd.concat([data_train, data_predict])
# print(len(data1.index))





data_train = data1[(data1.tf10 != 'null') & (data1.tf10 != '0')]
data_predict = data1[(data1.tf10 == 'null') | (data1.tf10 == '0')]
# print(len(data_train.index))
# print(len(data_predict.index))

# print(data_predict)

clf = svm.SVC(gamma=0.0001,C=100)
clf.fit( data_train[["Country","Year","tp10","tp5","tp1","t","tf1","tf5"]],data_train["tf10"])
data_predict["tf10"] = clf.predict(data_predict[["Country", "Year", "tp10","tp5","tp1","t","tf1","tf5"]])



# print(data_predict["tf10"][:3])
# print(data_predict)


data1 = pd.concat([data_train, data_predict])
# print(len(data1.index))
# print(data1[:3])


fulldata = pd.concat([data1, data2], axis=1)
print(len(fulldata.index))

print "*****************************************************"


data_train = fulldata[(fulldata.of1 != 'null') & (fulldata.of1 != '0')]
data_predict = fulldata[(fulldata.of1 == 'null') | (fulldata.of1 == '0')]
print(len(data_train.index))
print(len(data_predict.index))

# print(data_predict)

clf = svm.SVC(gamma=0.0001,C=100)
clf.fit( data_train[["Country","Year","tp10","tp5","tp1","t","tf1","tf5","tf10","item","op10","op5","op1","o"]],data_train["of1"])
data_predict["of1"] = clf.predict(data_predict[["Country", "Year", "tp10","tp5","tp1","t","tf1","tf5","tf10","item","op10","op5","op1","o"]])



# print(data_predict["tf10"][:3])
# print(data_predict)


fulldata = pd.concat([data_train, data_predict])
print(len(fulldata.index))
# print(fulldata[:3])




data_train = fulldata[(fulldata.of5 != 'null') & (fulldata.of5 != '0')]
data_predict = fulldata[(fulldata.of5 == 'null') | (fulldata.of5 == '0')]
print(len(data_train.index))
print(len(data_predict.index))

# print(data_predict)

clf = svm.SVC(gamma=0.0001,C=100)
clf.fit( data_train[["Country","Year","tp10","tp5","tp1","t","tf1","tf5","tf10","item","op10","op5","op1","o","of1"]],data_train["of5"])
data_predict["of5"] = clf.predict(data_predict[["Country", "Year", "tp10","tp5","tp1","t","tf1","tf5","tf10","item","op10","op5","op1","o","of1"]])



# print(data_predict["tf10"][:3])
# print(data_predict)


fulldata = pd.concat([data_train, data_predict])
print(len(fulldata.index))





data_train = fulldata[(fulldata.of10 != 'null') & (fulldata.of10 != '0')]
data_predict = fulldata[(fulldata.of10 == 'null') | (fulldata.of10 == '0')]
print(len(data_train.index))
print(len(data_predict.index))

# print(data_predict)

clf = svm.SVC(gamma=0.0001,C=100)
clf.fit( data_train[["Country","Year","tp10","tp5","tp1","t","tf1","tf5","tf10","item","op10","op5","op1","o","of1","of5"]],data_train["of10"])
data_predict["of10"] = clf.predict(data_predict[["Country", "Year", "tp10","tp5","tp1","t","tf1","tf5","tf10","item","op10","op5","op1","o","of1","of5"]])



# print(data_predict["tf10"][:3])

print "final prediction"
# print(data_predict)


fulldata = pd.concat([data_train, data_predict])
print(len(fulldata.index))
# print(fulldata[:3])



fulldata.to_csv('output.csv', sep=',', encoding='utf-8')