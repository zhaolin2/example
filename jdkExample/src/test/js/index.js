const http = require('http');

function createElement(jsonData, fieldsObj) {
    if (!jsonData || !fieldsObj) {
        console.warn('缺少传入参数')
        throw new Error('缺少传入参数')
    }
    // try{
    //     jsonData = JSON.parse(jsonData)
    //     fieldsObj = JSON.parse(fieldsObj)
    // }catch (e){
    //     console.warn(e)
    //     console.warn('传入json字符串转化失败')
    //     return
    // }

    let resultElement = '<div class="assembled">'
    resultElement += `<style>.assembled table{border-collapse:collapse;width:100%;}.assembled table th{border:1px solid #000;}.assembled table td{border:1px solid #000;}.assembled p{margin:0;padding:0;}.assembled .meta{margin-bottom:20px;}</style>`
    try {
        if (jsonData && Array.isArray(jsonData)) {
            jsonData.forEach(item => {//遍历所有消息对象，item:一条消息对象
                resultElement += '<div class="meta">'
                //meta.header数据，标题节点创建
                if (item.header) {
                    resultElement += '<div class="meta-headers">'
                    Object.keys(item.header).forEach(headerKey => {//遍历消息对象的所有文字描述记录
                        const fieldChName = fieldsObj[headerKey]
                        if (fieldChName) {
                            const desc = item.header[headerKey]
                            //一条haeder的节点
                            const element = `<p class="meta-header-item">
														<span class="meta-header-item-name">${fieldChName}</span>：
														<span class="meta-header-item-desc">${desc}</span>
													</p>`
                            resultElement += element
                        }
                    })
                    resultElement += '</div>'
                }
                //meta.lines数据，表格节点创建
                if (item.lines && Array.isArray(item.lines)) {
                    resultElement += '<table class="meta-table">'
                    //获取表格所有列的字段
                    const tableFileds = Object.keys(item.lines[0])
                    //创建表格标题行
                    resultElement += '<tr>'
                    //创建标题单元格
                    const thEls = tableFileds.map(filed => `<th>${fieldsObj[filed] || ''}</th>`).join('')
                    resultElement += thEls
                    resultElement += '</tr>'
                    //创建所有表格数据行
                    item.lines.forEach(line => {
                        resultElement += '<tr>'
                        const tdEls = tableFileds.map(filed => `<td>${line[filed]}</td>`).join('')
                        resultElement += tdEls
                        resultElement += '</tr>'
                    })
                    resultElement += '</table>'
                }
                resultElement += '</div>'
            })
            resultElement += '</div>'
            console.log(resultElement)
            return resultElement;
        } else {
            throw new Error('metas数据格式有误')
        }
    } catch (e) {
        console.log("构造响应错误",e)
        throw new Error('构造参数错误')
    }
}
//命令行的传入参数
// const commandParams = process.argv.slice(2)

// createElement(commandParams[0], commandParams[1])
const server = http.createServer((request, response) => {
    console.log('Received a request:');
    console.log('Method:', request.method);
    console.log('URL:', request.url);
    console.log('Headers:', request.headers);

    const { url } = request;
    if (url === '/') {
        let requestData = '';

        request.on('data', (chunk) => {
            requestData += chunk;
        });

        request.on('end', () => {
            console.log('Request body:', requestData);

            let bodyJson;
            let metaMap;
            let metaData;
            try {
                bodyJson = JSON.parse(requestData);
                metaMap = bodyJson['meta'];
                metaData = bodyJson['data'];


                const dataHtml = createElement(metaData, metaMap);
                response.statusCode = 200;
                response.setHeader('Content-Type', 'text/html');
                response.end(dataHtml);

            } catch (error) {
                console.error('Failed to parse JSON:', error);
                response.statusCode = 400; // Bad Request
                response.end('响应错误');
                return;
            }


        });
    } else {
        response.statusCode = 404;
        response.end('404 Not Found');
    }
});

const port = 3000;
server.listen(port, () => {
    console.log(`Server running at http://localhost:${port}/`);
});
