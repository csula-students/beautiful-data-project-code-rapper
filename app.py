import pandas as pd

rowlist = []

# f = open("dataset.tsv",'r+')

with open('dataset.tsv') as f:
	rowlist = [line.strip().split('\t') for line in f]


print("number of columns in input file is: ", len(rowlist[0]))
print("assuming the file has no header")
print("row count in input file is: ", len(rowlist))
print(rowlist[:1])
 
rowlist = rowlist[:500]
print("applying the problem only for: ", len(rowlist), " rows")


data = pd.DataFrame({
	"Country" : [item[0] for item in rowlist],
	"Year": [item[1] for item in rowlist],
	"t": [item[3] for item in rowlist],
	"tp10": [item[9] for item in rowlist],
	"item": [item[2] for item in rowlist],
    "o": [item[10] for item in rowlist],
    "op10": [item[16] for item in rowlist]
    })

print("converting to pandas dataframes")
print("total rows in the dataframe: ",len(data))
print("cleaning up null value rows for the essential fields")

data = data[(data.Country != 'null') & (data.Year != 'null') & (data.t != 'null')  & (data.t != '0') & (data.item != 'null')  & (data.o != 'null') & (data.o != '0')]
print("total rows in the dataframe: ",len(data))
print("making category columns for country and item")
data["Country"] = data["Country"].astype('category')
data["item"] = data["item"].astype('category')
print(data[:3])
data["Country"] = data["Country"].cat.codes
data["item"] = data["item"].cat.codes
print(data[:3])
data1 = data[["Country","Year","t","tp10"]]
data2 = data[["item","o","op10"]]



data_train = data1[(data1.tp10 != 'null') & (data1.tp10 != '0')]
data_predict = data1[(data1.tp10 == 'null') | (data1.tp10 == '0')]
print(len(data_train.index))
print(len(data_predict.index))


from sklearn import svm,cross_validation
clf = svm.SVC(gamma=0.0001,C=100)
clf.fit( data_train[["Country","Year","t"]],data_train["tp10"])
data_predict["tp10"] = clf.predict(data_predict[["Country", "Year", "t"]])



print(data_predict["tp10"][:3])
print(data_predict[:3])
print(cross_validation.cross_val_score(clf,data_train[["Country", "Year", "t"]], data_train["tp10"], scoring='mean_absolute_error'))
 
data1 = pd.concat([data_train, data_predict])
print(len(data1.index))
print(data1[:3])
 
fulldata = pd.concat([data1, data2], axis=1)
data_train = fulldata[(fulldata.op10 != 'null') & (fulldata.op10 != '0')]
data_predict = fulldata[(fulldata.op10 == 'null') | (fulldata.op10 == '0')]
print(len(data_train.index))
print(len(data_predict.index))
 
clf = svm.SVC(gamma=0.001, C=100.)
clf.fit(data_train[["Country", "Year", "t", "tp10", "item", "o"]], data_train["op10"])
data_predict["op10"] = clf.predict(data_predict[["Country", "Year", "t", "tp10", "item", "o"]])
print(data_predict["op10"][:3])
print(data_predict[:3])
print(cross_validation.cross_val_score(clf,data_train[["Country", "Year", "t", "tp10", "item", "o"]], data_train["op10"], scoring='mean_absolute_error'))