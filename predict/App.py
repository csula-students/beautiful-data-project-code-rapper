country_dict = dict()

with open('dataset1.tsv') as f:
    for line in f:
        if line.split('\t')[0] in country_dict:
            country_dict[line.split('\t')[0]].append(line)
        else:
            country_dict[line.split('\t')[0]] = [line]



# print country_dict


for key, value in country_dict.iteritems() :
    print key, len(value)
