#!/bin/bash
git config user.name liuchenglong
git config user.email liuchenglong125@foxmail.com
currentBranchName=$(git branch --show-current)
commitMsg=$(date "+%Y-%m-%d %H:%M:%S")
echo "当前分支: $currentBranchName"
echo "提交内容: $commitMsg"
git add .
git commit -m "$commitMsg"
git pull origin $currentBranchName
git push origin $currentBranchName || (echo "同步到远程仓库失败")
echo "提交完成"
